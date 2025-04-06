package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RideDriverService;
import by.ikrotsyuk.bsuir.ridesservice.service.tools.SortTool;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    @Transactional(readOnly = true)
    public Page<RideResponseDTO> getAvailableRides(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        // get drivers car class
        Page<RideEntity> rideEntities = rideRepository.findAllByRideStatus(RideStatusTypes.PENDING,
                PageRequest.of(offset, itemCount,
                        SortTool.getSort(field, isSortDirectionAsc)));
        if(!rideEntities.hasContent())
            throw new RuntimeException("ex");
        return rideEntities.map(rideMapper::toDTO);
    }

    @Override
    @Transactional
    public RideFullResponseDTO acceptRide(Long driverId, Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        rideEntity.setDriverId(driverId);
        rideEntity.setAcceptedAt(OffsetDateTime.now());
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO refuseRide(Long driverId, Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        Random rand = new Random();
        rideEntity.setDriverId(null);
        rideEntity.setRideStatus(RideStatusTypes.CANCELED);
        rideEntity.setAcceptedAt(null);
        rideEntity.setBeganAt(null);
        rideEntity.setEstimatedWaitingTime(rand.nextInt(500) + 100);
        return null;
    }

    @Override
    @Transactional
    public RideFullResponseDTO beginRide(Long driverId, Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        if(!rideEntity.getDriverId().equals(driverId))
            throw new RuntimeException("ex");
        OffsetDateTime now = OffsetDateTime.now();
        long timeDifferenceInSeconds = Duration.between(rideEntity.getAcceptedAt(), now).getSeconds();
        rideEntity.setBeganAt(now);
        rideEntity.setRideStatus(RideStatusTypes.IN_PROGRESS);
        rideEntity.setEstimatedWaitingTime((int) timeDifferenceInSeconds);
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO endRide(Long driverId, Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        if(!rideEntity.getDriverId().equals(driverId))
            throw new RuntimeException("ex");
        rideEntity.setRideStatus(RideStatusTypes.COMPLETED);
        rideEntity.setEndedAt(OffsetDateTime.now());
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RideFullResponseDTO> getRidesHistory(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        Page<RideEntity> rideEntities = rideRepository.findAllByDriverId(driverId,
                PageRequest.of(offset, itemCount,
                        SortTool.getSort(field, isSortDirectionAsc)));
        if(!rideEntities.hasContent())
            throw new RuntimeException("ex");
        return rideEntities.map(rideMapper::toFullDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public RideFullResponseDTO getCurrentRide(Long driverId) {
        RideEntity rideEntity = rideRepository.findByDriverIdAndRideStatus(driverId, RideStatusTypes.IN_PROGRESS)
                .orElseThrow(() -> new RuntimeException("ex"));
        return rideMapper.toFullDTO(rideEntity);
    }
}
