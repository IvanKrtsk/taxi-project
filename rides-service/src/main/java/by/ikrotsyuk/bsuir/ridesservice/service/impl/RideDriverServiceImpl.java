package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.FinishRideIncomePaymentDTO;
import by.ikrotsyuk.bsuir.communicationparts.event.IncomePaymentArtemisDTO;
import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions.*;
import by.ikrotsyuk.bsuir.ridesservice.feign.RidesDriverClient;
import by.ikrotsyuk.bsuir.ridesservice.feign.RidesPaymentClient;
import by.ikrotsyuk.bsuir.ridesservice.feign.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RideDriverService;
import by.ikrotsyuk.bsuir.ridesservice.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class RideDriverServiceImpl implements RideDriverService {
    private final RideMapper rideMapper;
    private final RideRepository rideRepository;
    private final PaginationUtil paginationUtil;
    private final JmsTemplate jmsTemplate;
    private final RidesPaymentClient ridesPaymentClient;
    private final RidesDriverClient ridesDriverClient;
    private final String queueName = "incomePaymentsQueue";

    @Override
    @Transactional(readOnly = true)
    public Page<RideResponseDTO> getAvailableRides(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        isDriverExists(driverId);
        Page<RideEntity> rideEntities = rideRepository.findAllByRideStatus(RideStatusTypes.PENDING,
                paginationUtil.getPageRequest(offset, itemCount, field, isSortDirectionAsc));
        if(!rideEntities.hasContent())
            throw new AvailableRidesNotFoundException(driverId);
        return rideEntities.map(rideMapper::toDTO);
    }

    @Override
    @Transactional
    public RideFullResponseDTO acceptRide(Long driverId, Long rideId) {
        isDriverExists(driverId);
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        Long id = rideEntity.getDriverId();
        if(rideEntity.getRideStatus() != RideStatusTypes.PENDING)
            throw new RideIsNotInRightConditionForThisOperationException(rideId, rideEntity.getRideStatus());
        if(rideEntity.getDriverId() != null) {
            if(id.equals(driverId))
                throw new RideAlreadyAcceptedByYouException(rideId);
            throw new RideAlreadyAcceptedByAnotherDriverException(rideId);
        }
        rideEntity.setDriverId(driverId);
        rideEntity.setRideStatus(RideStatusTypes.IN_PROGRESS);
        rideEntity.setAcceptedAt(OffsetDateTime.now());
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO refuseRide(Long driverId, Long rideId) {
        isDriverExists(driverId);
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        if(!rideEntity.getDriverId().equals(driverId))
            throw new RideNotBelongToDriverException(rideId, driverId);
        Random rand = new Random();
        rideEntity.setDriverId(null);
        rideEntity.setRideStatus(RideStatusTypes.PENDING);
        rideEntity.setAcceptedAt(null);
        rideEntity.setBeganAt(null);
        rideEntity.setEstimatedWaitingTime(rand.nextInt(500) + 100);
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO beginRide(Long driverId, Long rideId) {
        isDriverExists(driverId);
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        if(rideEntity.getRideStatus() != RideStatusTypes.IN_PROGRESS)
            throw new RideIsNotInRightConditionForThisOperationException(rideId, rideEntity.getRideStatus());
        if(!rideEntity.getDriverId().equals(driverId))
            throw new RideNotBelongToDriverException(rideId, driverId);
        OffsetDateTime now = OffsetDateTime.now();
        long timeDifferenceInSeconds = Duration.between(rideEntity.getAcceptedAt(), now).getSeconds();
        rideEntity.setBeganAt(now);
        rideEntity.setEstimatedWaitingTime((int) timeDifferenceInSeconds);

        PaymentTypes paymentType = (rideEntity.getPaymentType().name().equals(PaymentTypeTypes.CARD.name())) ? PaymentTypes.CARD : PaymentTypes.CASH;
        IncomePaymentArtemisDTO incomePaymentArtemisDTO = new IncomePaymentArtemisDTO(rideId, rideEntity.getPassengerId(), driverId, rideEntity.getCost(), paymentType);
        jmsTemplate.convertAndSend(queueName, incomePaymentArtemisDTO);

        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO endRide(Long driverId, Long rideId) {
        isDriverExists(driverId);
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        if(rideEntity.getRideStatus() != RideStatusTypes.IN_PROGRESS)
            throw new RideIsNotInRightConditionForThisOperationException(rideId, rideEntity.getRideStatus());
        if(!rideEntity.getDriverId().equals(driverId))
            throw new RideNotBelongToDriverException(rideId, driverId);

        FinishRideIncomePaymentDTO rideIncomePaymentDTO;
        try{
            rideIncomePaymentDTO = ridesPaymentClient.finishIncomePayment(rideEntity.getPassengerId()).getBody();
        }catch (feign.RetryableException e){
            throw new FeignConnectException();
        }
        PaymentTypeTypes paymentType = (PaymentTypeTypes.CARD.name().equals(rideIncomePaymentDTO.paymentType().name())) ? PaymentTypeTypes.CARD : PaymentTypeTypes.CASH;
        rideEntity.setPaymentType(paymentType);
        rideEntity.setRideStatus(RideStatusTypes.COMPLETED);
        rideEntity.setEndedAt(OffsetDateTime.now());
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RideFullResponseDTO> getRidesHistory(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        isDriverExists(driverId);
        Page<RideEntity> rideEntities = rideRepository.findAllByDriverId(driverId, paginationUtil.getPageRequest(offset, itemCount, field, isSortDirectionAsc));
        if(!rideEntities.hasContent())
            throw new RidesNotFoundException();
        return rideEntities.map(rideMapper::toFullDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public RideFullResponseDTO getCurrentRide(Long driverId) {
        isDriverExists(driverId);
        RideEntity rideEntity = rideRepository.findByDriverIdAndRideStatus(driverId, RideStatusTypes.IN_PROGRESS)
                .orElseThrow(() -> new CurrentRideNotFoundException(driverId));
        return rideMapper.toFullDTO(rideEntity);
    }

    private void isDriverExists(Long driverId){
        DriverResponseDTO driverResponseDTO;
        try{
            driverResponseDTO = ridesDriverClient.getDriverProfile(driverId).getBody();
        }catch (feign.RetryableException e){
            throw new FeignConnectException();
        }
    }
}
