package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.mapper.VehicleMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.repository.VehicleRepository;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final DriverRepository driverRepository;
    private final Set<String> carClassSet = new HashSet<>();

    /**
     * в будущем планирую написать аннотацию для проверки принадлежности enum с помощью post processor
     */
    @PostConstruct
    public void init(){
        for(CarClassTypes carClass : CarClassTypes.values())
            carClassSet.add(carClass.name());
    }

    /**
     * в будущем планирую написать аннотацию для проверки принадлежности enum с помощью post processor
     */
    @Override
    @Transactional
    public VehicleResponseDTO addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("not found"));

        CarClassTypes type = vehicleRequestDTO.getCarClass();
        if(!checkCarClassValid(type))
            vehicleRequestDTO.setCarClass(CarClassTypes.ECONOMY);

        VehicleEntity vehicleEntity = vehicleMapper.toEntity(vehicleRequestDTO);
        vehicleEntity.setDriver(driverEntity);
        return vehicleMapper.toDTO(vehicleRepository.save(vehicleEntity));
    }

    /**
     * в будущем планирую написать аннотацию для проверки принадлежности enum с помощью post processor
     */
    @Override
    @Transactional
    public VehicleResponseDTO editVehicle(Long driverId, Long vehicleId, VehicleRequestDTO vehicleRequestDTO) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("not found"));
        if(!Objects.equals(vehicleEntity.getDriver().getId(), driverId))
            throw new RuntimeException("not your car");
        else{
            vehicleEntity.setBrand(vehicleRequestDTO.getBrand());
            vehicleEntity.setModel(vehicleRequestDTO.getModel());

            CarClassTypes type = vehicleRequestDTO.getCarClass();
            if(!checkCarClassValid(type))
                type = CarClassTypes.ECONOMY;

            vehicleEntity.setCarClass(type);
            vehicleEntity.setYear(vehicleRequestDTO.getYear());
            vehicleEntity.setLicensePlate(vehicleRequestDTO.getLicensePlate());
            vehicleEntity.setColor(vehicleRequestDTO.getColor());
        }
        return null;
    }

    @Override
    @Transactional
    public VehicleResponseDTO chooseCurrentVehicle(Long driverId, Long vehicleId) {
        if(!driverRepository.existsById(driverId))
            throw new RuntimeException("driver not found");
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("car not found"));

        List<VehicleEntity> vehicleEntityList = vehicleRepository.findAllByDriverId(driverId)
                .orElseThrow(() -> new RuntimeException("driver has no cars"));

        if(!vehicleEntity.getDriver().getId().equals(driverId))
            throw new RuntimeException("car not yours");

        vehicleEntityList.forEach(vehicle ->
                vehicle.setIsCurrent(vehicle.getId().equals(vehicleId))
        );

        return vehicleMapper.toDTO(vehicleEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDTO getVehicleById(Long vehicleId) {
        return vehicleMapper.toDTO(vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehicles(int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<VehicleEntity> vehicleEntityPage = vehicleRepository.findAll(
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        );
        if(vehicleEntityPage.isEmpty())
            throw new RuntimeException("not found");
        else
            return vehicleMapper.toDTOPage(vehicleEntityPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByType(CarClassTypes type, int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return vehicleMapper.toDTOPage(vehicleRepository.findAllByCarClass(type,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        ).orElseThrow(() -> new RuntimeException("not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByYear(Integer year, int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return vehicleMapper.toDTOPage(vehicleRepository.findAllByYear(year,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        ).orElseThrow(() -> new RuntimeException("not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByBrand(String brand, int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return vehicleMapper.toDTOPage(vehicleRepository.findAllByBrand(brand,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        ).orElseThrow(() -> new RuntimeException("not found")));
    }

    private boolean checkCarClassValid(CarClassTypes type){
        return carClassSet.contains(type.name());
    }
}
