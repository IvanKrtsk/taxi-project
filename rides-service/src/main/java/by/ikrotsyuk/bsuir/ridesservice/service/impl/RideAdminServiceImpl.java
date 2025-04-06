package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions.RideNotFoundByIdException;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions.RidesNotFoundException;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RideAdminService;
import by.ikrotsyuk.bsuir.ridesservice.service.tools.SortTool;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RideAdminServiceImpl implements RideAdminService {
    private final RideMapper rideMapper;
    private final RideRepository rideRepository;

    @Override
    @Transactional
    public RideFullResponseDTO editRide(Long rideId, RideFullRequestDTO rideFullRequestDTO) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        rideEntity.setStartLocation(rideFullRequestDTO.startLocation());
        rideEntity.setEndLocation(rideFullRequestDTO.endLocation());
        rideEntity.setCost(rideFullRequestDTO.cost());

        rideEntity.setRideStatus(rideFullRequestDTO.rideStatus());
        rideEntity.setPaymentType(rideFullRequestDTO.paymentType());

        rideEntity.setEstimatedWaitingTime(rideFullRequestDTO.estimatedWaitingTime());
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional
    public RideFullResponseDTO deleteRide(Long rideId) {
        RideEntity rideEntity = rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId));
        rideRepository.deleteById(rideId);
        return rideMapper.toFullDTO(rideEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RideFullResponseDTO> getAllRides(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        Page<RideEntity> rideEntities = rideRepository.findAll(
                PageRequest.of(offset, itemCount,
                        SortTool.getSort(field, isSortDirectionAsc))
        );
        if(!rideEntities.hasContent())
            throw new RidesNotFoundException();
        return rideEntities.map(rideMapper::toFullDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public RideFullResponseDTO getRideById(Long rideId) {
        return rideMapper.toFullDTO(rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundByIdException(rideId)));
    }
}
