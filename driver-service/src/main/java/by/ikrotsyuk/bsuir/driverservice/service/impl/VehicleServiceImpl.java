package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverVehiclesNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.*;
import by.ikrotsyuk.bsuir.driverservice.mapper.VehicleMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.repository.VehicleRepository;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import by.ikrotsyuk.bsuir.driverservice.service.validation.VehicleServiceValidationManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final DriverRepository driverRepository;
    private final VehicleServiceValidationManager vehicleServiceValidationManager;
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
                .orElseThrow(() -> new DriverNotFoundByIdException(driverId));

        vehicleServiceValidationManager.checkLicensePlateIsUnique(vehicleRequestDTO.getLicensePlate());

        CarClassTypes type = vehicleRequestDTO.getCarClass();
        if(!checkCarClassValid(type))
            vehicleRequestDTO.setCarClass(CarClassTypes.ECONOMY);

        VehicleEntity vehicleEntity = vehicleMapper.toEntityWithDefault(vehicleRequestDTO);
        vehicleEntity.setDriver(driverEntity);
        return vehicleMapper.toDTO(vehicleRepository.save(vehicleEntity));
    }

    /**
     * в будущем планирую написать аннотацию для проверки принадлежности enum с помощью post processor
     */
    @Override
    @Transactional
    public VehicleResponseDTO editVehicle(Long driverId, Long vehicleId, VehicleRequestDTO vehicleRequestDTO) {
        checkDriverAndVehicleId(driverId, vehicleId);
        vehicleServiceValidationManager.checkLicensePlateIsUnique(vehicleRequestDTO.getLicensePlate());

        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundByIdException(vehicleId));

        vehicleEntity.setBrand(vehicleRequestDTO.getBrand());
        vehicleEntity.setModel(vehicleRequestDTO.getModel());
        CarClassTypes type = vehicleRequestDTO.getCarClass();
        if(!checkCarClassValid(type))
            type = CarClassTypes.ECONOMY;
        vehicleEntity.setCarClass(type);
        vehicleEntity.setYear(vehicleRequestDTO.getYear());
        vehicleEntity.setLicensePlate(vehicleRequestDTO.getLicensePlate());
        vehicleEntity.setColor(vehicleRequestDTO.getColor());
        return vehicleMapper.toDTO(vehicleEntity);
    }

    @Override
    @Transactional
    public VehicleResponseDTO chooseCurrentVehicle(Long driverId, Long vehicleId) {
        checkDriverAndVehicleId(driverId, vehicleId);

        List<VehicleEntity> vehicleEntityList = vehicleRepository.findAllByDriverId(driverId)
                .orElseThrow(() -> new DriverVehiclesNotFoundException(driverId));
        vehicleEntityList.forEach(vehicle ->
            vehicle.setIsCurrent(vehicle.getId().equals(vehicleId))
        );
        vehicleRepository.saveAll(vehicleEntityList);

        return vehicleMapper.toDTO(vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundByIdException(vehicleId)));
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDTO getVehicleById(Long vehicleId) {
        return vehicleMapper.toDTO(vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundByIdException(vehicleId)));
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
            throw new VehiclesNotFoundException();
        else
            return vehicleEntityPage.map(vehicleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByType(CarClassTypes type, int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return vehicleRepository.findAllByCarClass(type,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field)))
                .orElseThrow(() -> new VehiclesNotFoundByTypeException(type))
                .map(vehicleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByYear(Integer year, int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return vehicleRepository.findAllByYear(year,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field)))
                .orElseThrow(() -> new VehiclesNotFoundByYearException(year))
                .map(vehicleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByBrand(String brand, int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return vehicleRepository.findAllByBrand(brand,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field)))
                .orElseThrow(() -> new VehiclesNotFoundByBrandException(brand))
                .map(vehicleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDTO getVehicleByLicense(String licensePlate) {
        return vehicleMapper.toDTO(vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new VehicleNotFoundByLicensePlateException(licensePlate)));
    }

    @Override
    @Transactional
    public VehicleResponseDTO deleteVehicleById(Long driverId, Long vehicleId) {
        checkDriverAndVehicleId(driverId, vehicleId);
        VehicleResponseDTO vehicleResponseDTO = vehicleMapper.toDTO(vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundByIdException(vehicleId)));
        vehicleRepository.deleteById(vehicleId);
        return vehicleResponseDTO;
    }

    private boolean checkCarClassValid(CarClassTypes type){
        return carClassSet.contains(type.name());
    }

    @Transactional(readOnly = true)
    protected void checkDriverAndVehicleId(Long driverId, Long vehicleId){
        if(!driverRepository.existsById(driverId))
            throw new DriverNotFoundByIdException(driverId);

        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundByIdException(vehicleId));

        if(!vehicleEntity.getDriver().getId().equals(driverId))
            throw new VehicleNotBelongToDriverException(vehicleId, driverId);
    }
}
