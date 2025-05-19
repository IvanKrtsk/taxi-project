package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions.*;
import by.ikrotsyuk.bsuir.ridesservice.feign.RidesPassengerClient;
import by.ikrotsyuk.bsuir.ridesservice.feign.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RidePassengerService;
import by.ikrotsyuk.bsuir.ridesservice.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class RidePassengerServiceImpl implements RidePassengerService {
    private final RideMapper rideMapper;
    private final RideRepository rideRepository;
    private final PaginationUtil paginationUtil;
    private final RidesPassengerClient ridesPassengerClient;

    private final BigDecimal ECONOMY_MULTIPLIER = BigDecimal.valueOf(1.0);
    private final BigDecimal COMFORT_MULTIPLIER = BigDecimal.valueOf(1.2);
    private final BigDecimal COMFORT_PLUS_MULTIPLIER = BigDecimal.valueOf(1.5);
    private final BigDecimal BUSINESS_MULTIPLIER = BigDecimal.valueOf(2.0);
    private final BigDecimal ELECTRIC_MULTIPLIER = BigDecimal.valueOf(1.3);
    private final BigDecimal TRUCK_MULTIPLIER = BigDecimal.valueOf(3.0);
    private final BigDecimal ULTIMA_MULTIPLIER = BigDecimal.valueOf(4.0);
    private final BigDecimal DELIVERY_MULTIPLIER = BigDecimal.valueOf(1.1);

    @Override
    public BigDecimal getCostOfRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        isPassengerExists(passengerId);
        return calculatePrice(rideRequestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RideFullResponseDTO> getRidesStory(Long passengerId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        isPassengerExists(passengerId);
        Page<RideEntity> rideEntities = rideRepository.
                findAllByPassengerId(passengerId, paginationUtil.getPageRequest(offset, itemCount, field, isSortDirectionAsc));
        if(!rideEntities.hasContent())
            throw new RidesNotFoundException();
        return rideEntities.map(rideMapper::toFullDTO);
    }

    @Override
    @Transactional
    public RideResponseDTO bookRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        isPassengerExists(passengerId);
        RideEntity rideEntity = rideMapper.toEntity(rideRequestDTO);
        Random rand = new Random();
        rideEntity.setCost(calculatePrice(rideRequestDTO));
        rideEntity.setPassengerId(passengerId);
        rideEntity.setRideStatus(RideStatusTypes.PENDING);
        rideEntity.setEstimatedWaitingTime(rand.nextInt(500) + 100); // analyze drivers positions
        return rideMapper.toDTO(rideRepository.save(rideEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public RideFullResponseDTO getRideInfo(Long passengerId, Long rideId) {
        isPassengerExists(passengerId);
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        if(!rideEntity.getPassengerId().equals(passengerId))
            throw new RideNotBelongToPassengerException(rideId, passengerId);
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO cancelRide(Long passengerId, Long rideId) {
        isPassengerExists(passengerId);
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        if(!rideEntity.getPassengerId().equals(passengerId))
            throw new RideNotBelongToPassengerException(rideId, passengerId);
        if(rideEntity.getRideStatus() == RideStatusTypes.CANCELED)
            throw new RideIsNotInRightConditionForThisOperationException(rideId, rideEntity.getRideStatus());
        rideEntity.setRideStatus(RideStatusTypes.CANCELED);
        return rideMapper.toFullDTO(rideEntity);
    }

    private BigDecimal calculatePrice(RideRequestDTO rideRequestDTO){
        int hash = Objects.hash(rideRequestDTO.startLocation(), rideRequestDTO.endLocation());
        hash = Math.abs(hash);
        BigDecimal price = BigDecimal.valueOf( 2 + (hash % 99));
        return price.multiply(getMultiplier(rideRequestDTO.carClass()));
    }

    private BigDecimal getMultiplier(CarClassTypes carClass) {
        return switch (carClass) {
            case ECONOMY -> ECONOMY_MULTIPLIER;
            case COMFORT -> COMFORT_MULTIPLIER;
            case COMFORT_PLUS -> COMFORT_PLUS_MULTIPLIER;
            case BUSINESS -> BUSINESS_MULTIPLIER;
            case ELECTRIC -> ELECTRIC_MULTIPLIER;
            case TRUCK -> TRUCK_MULTIPLIER;
            case ULTIMA -> ULTIMA_MULTIPLIER;
            case DELIVERY -> DELIVERY_MULTIPLIER;
        };
    }

    private void isPassengerExists(Long passengerId){
        PassengerResponseDTO passengerResponseDTO;
        try{
            passengerResponseDTO = ridesPassengerClient.getPassengerProfile(passengerId).getBody();
        }catch (feign.RetryableException e){
            throw new FeignConnectException();
        }
    }
}
