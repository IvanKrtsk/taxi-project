package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RidePassengerService;
import by.ikrotsyuk.bsuir.ridesservice.service.tools.SortTool;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public BigDecimal getCostOfRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        return calculatePrice(rideRequestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RideFullResponseDTO> getRidesStory(Long passengerId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        // check if passenger exists
        Page<RideEntity> rideEntities = rideRepository.
                findAllByPassengerId(passengerId, PageRequest.of(offset, itemCount,
                        SortTool.getSort(field, isSortDirectionAsc)));
        if(!rideEntities.hasContent())
            throw new RuntimeException("ex");
        return rideEntities.map(rideMapper::toFullDTO);
    }

    @Override
    @Transactional
    public RideResponseDTO bookRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        // check if passenger exists and not in ride right now
        RideEntity rideEntity = rideMapper.toEntity(rideRequestDTO);
        Random rand = new Random();
        rideEntity.setCost(calculatePrice(rideRequestDTO));
        rideEntity.setPassengerId(passengerId);
        rideEntity.setRideStatus(RideStatusTypesRides.PENDING);
        rideEntity.setEstimatedWaitingTime(rand.nextInt(500) + 100); // analyze drivers positions
        return rideMapper.toDTO(rideRepository.save(rideEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public RideFullResponseDTO getRideInfo(Long passengerId, Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        if(!rideEntity.getPassengerId().equals(passengerId))
            throw new RuntimeException("ex");
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO refuseRide(Long passengerId, Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        if(!rideEntity.getPassengerId().equals(passengerId))
            throw new RuntimeException("ex");
        rideEntity.setRideStatus(RideStatusTypesRides.CANCELED);
        return rideMapper.toFullDTO(rideEntity);
    }

    private BigDecimal calculatePrice(RideRequestDTO rideRequestDTO){
        int hash = Objects.hash(rideRequestDTO.startLocation(), rideRequestDTO.endLocation());
        hash = Math.abs(hash);
        BigDecimal price = BigDecimal.valueOf( 2 + (hash % 99));
        return price.multiply(getMultiplier(rideRequestDTO.carClass()));
    }

    private static BigDecimal getMultiplier(CarClassTypesRides carClass) {
        return switch (carClass) {
            case ECONOMY -> BigDecimal.valueOf(1.0);
            case COMFORT -> BigDecimal.valueOf(1.2);
            case COMFORT_PLUS -> BigDecimal.valueOf(1.5);
            case BUSINESS -> BigDecimal.valueOf(2.0);
            case ELECTRIC -> BigDecimal.valueOf(1.3);
            case TRUCK -> BigDecimal.valueOf(3.0);
            case ULTIMA -> BigDecimal.valueOf(4.0);
            case DELIVERY -> BigDecimal.valueOf(1.1);
        };
    }
}
