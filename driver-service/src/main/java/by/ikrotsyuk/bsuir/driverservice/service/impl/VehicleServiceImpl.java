package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverCurrentVehicleNotFoundException;
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
import java.util.Optional;
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

        vehicleServiceValidationManager.checkLicensePlateIsUnique(vehicleRequestDTO.licensePlate());

        CarClassTypes type = vehicleRequestDTO.carClass();
        if(!checkCarClassValid(type))
            vehicleRequestDTO = new VehicleRequestDTO(vehicleRequestDTO.brand(), vehicleRequestDTO.model(), CarClassTypes.ECONOMY, vehicleRequestDTO.year(), vehicleRequestDTO.licensePlate(), vehicleRequestDTO.color());

        Optional<List<VehicleEntity>> optionalVehicleEntityList = vehicleRepository.findAllByDriverId(driverId);
        optionalVehicleEntityList.ifPresent(vehicleEntityList -> vehicleEntityList.forEach(vehicle ->
                vehicle.setIsCurrent(false)
        ));

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
        vehicleServiceValidationManager.checkLicensePlateIsUnique(vehicleRequestDTO.licensePlate());

        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundByIdException(vehicleId));

        vehicleEntity.setBrand(vehicleRequestDTO.brand());
        vehicleEntity.setModel(vehicleRequestDTO.model());
        CarClassTypes type = vehicleRequestDTO.carClass();
        if(!checkCarClassValid(type))
            type = CarClassTypes.ECONOMY;
        vehicleEntity.setCarClass(type);
        vehicleEntity.setYear(vehicleRequestDTO.year());
        vehicleEntity.setLicensePlate(vehicleRequestDTO.licensePlate());
        vehicleEntity.setColor(vehicleRequestDTO.color());
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
    public Page<VehicleResponseDTO> getAllVehicles(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<VehicleEntity> vehicleEntities = vehicleRepository.findAll(
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        );
        if(!vehicleEntities.hasContent())
            throw new VehiclesNotFoundException();
        else
            return vehicleEntities.map(vehicleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByType(CarClassTypes type, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;

        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<VehicleEntity> vehicleEntities = vehicleRepository.findAllByCarClass(type,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field)));

        if(!vehicleEntities.hasContent())
            throw new VehiclesNotFoundByTypeException(type);
        return vehicleEntities.map(vehicleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByYear(Integer year, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;

        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<VehicleEntity> vehicleEntities = vehicleRepository.findAllByYear(year,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field)));

        if(!vehicleEntities.hasContent())
            throw new VehiclesNotFoundByYearException(year);
        return vehicleEntities.map(vehicleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDTO> getAllVehiclesByBrand(String brand, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;

        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<VehicleEntity> vehicleEntities = vehicleRepository.findAllByBrand(brand,
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field)));

        if(!vehicleEntities.hasContent())
            throw new VehiclesNotFoundByBrandException(brand);
        return vehicleEntities.map(vehicleMapper::toDTO);
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

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> getAllDriverVehicles(Long driverId) {
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(driverId));
        List<VehicleEntity> vehicleResponseDTOList = driverEntity.getDriverVehicles();
        if(vehicleResponseDTOList.isEmpty())
            throw new DriverVehiclesNotFoundException(driverId);
        return vehicleMapper.toDTOList(vehicleResponseDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDTO getDriverCurrentVehicle(Long driverId) {
        return getAllDriverVehicles(driverId).stream()
                .filter(VehicleResponseDTO::isCurrent)
                .findFirst()
                .orElseThrow(() -> new DriverCurrentVehicleNotFoundException(driverId));
    }
}
