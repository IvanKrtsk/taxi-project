package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RideAdminService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RideAdminServiceImpl implements RideAdminService {
    private final RideMapper rideMapper;
    private final RideRepository rideRepository;
    private final Set<String> rideStatusSet = new HashSet<String>();

    @PostConstruct
    public void init(){

    }

    @Override
    public RideFullResponseDTO editRide(Long rideId, RideFullRequestDTO rideFullRequestDTO) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        rideEntity.setStartLocation(rideFullRequestDTO.startLocation());
        rideEntity.setEndLocation(rideFullRequestDTO.endLocation());
        rideEntity.setCost(rideFullRequestDTO.cost());

        rideEntity.setRideStatus(rideFullRequestDTO.rideStatus());
        rideEntity.setPaymentType(rideFullRequestDTO.paymentType());

        rideEntity.setRating(rideFullRequestDTO.rating());
        rideEntity.setEstimatedWaitingTime(rideFullRequestDTO.estimatedWaitingTime());
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    public RideFullResponseDTO deleteRide(Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex"));
        rideRepository.deleteById(rideId);
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    public Page<RideFullResponseDTO> getAllRides(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<RideEntity> rideEntities = rideRepository.findAll(
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        );
        if(!rideEntities.hasContent())
            throw new RuntimeException("ex");
        return null;
    }

    @Override
    public RideFullResponseDTO getRideById(Long rideId) {
        return rideMapper.toFullDTO(rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("ex")));
    }
}
