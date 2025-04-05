package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RidePassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

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
    public Page<RideFullResponseDTO> getRidesStory(Long passengerId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        // check if passenger exists
        Page<RideEntity> rideEntities = rideRepository.
                findAllByPassengerId(passengerId, PageRequest.of(offset, itemCount,
                        getSort(field, isSortDirectionAsc)));
        if(rideEntities.hasContent())
            return rideEntities.map(rideMapper::toFullDTO);
        else
            throw new RuntimeException("ex");
    }

    @Override
    public RideResponseDTO bookRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        // check if passenger exists and not in ride right now
        return rideMapper.toDTO(rideRepository.save(rideMapper.toEntity(rideRequestDTO)));
    }

    @Override
    public RideFullResponseDTO getRideInfo(Long passengerId, Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        if(!rideEntity.getPassengerId().equals(passengerId))
            throw new RuntimeException("ex");
        return rideMapper.toFullDTO(rideEntity);
    }

    private BigDecimal calculatePrice(RideRequestDTO rideRequestDTO){
        int hash = Objects.hash(rideRequestDTO.startLocation(), rideRequestDTO.endLocation());
        hash = Math.abs(hash);
        BigDecimal price = BigDecimal.valueOf( 2 + (hash % 99));
        return price.multiply(getMultiplier(rideRequestDTO.carClass()));
    }

    private static BigDecimal getMultiplier(CarClassTypes carClass) {
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

    private Sort getSort(String field, Boolean isSortDirectionAsc){
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(sortDirection, field);
    }
}
