package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.mapper.VehicleMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.VehicleRepository;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public VehicleResponseDTO addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public VehicleResponseDTO editVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public Boolean makeVehicleCurrent(Long driverId, Long vehicleId) {
        return null;
    }

    @Override
    public Boolean makeVehicleUncurrent(Long driverId, Long vehicleId) {
        return null;
    }

    @Override
    public List<VehicleResponseDTO> getAllVehiclesByType(CarClassTypes type) {
        return List.of();
    }

    @Override
    public List<VehicleResponseDTO> getAllVehiclesByYear(Integer year) {
        return List.of();
    }

    @Override
    public List<VehicleResponseDTO> getAllVehiclesByBrand(String brand) {
        return List.of();
    }
}
