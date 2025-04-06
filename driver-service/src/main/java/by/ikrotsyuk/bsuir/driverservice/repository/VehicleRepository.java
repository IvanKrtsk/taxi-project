package by.ikrotsyuk.bsuir.driverservice.repository;

import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypesDriver;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<List<VehicleEntity>> findAllByDriverId(Long driverId);
    boolean existsById(Long id);
    boolean existsByLicensePlate(String licensePlate);
    Page<VehicleEntity> findAllByCarClass(@NotNull CarClassTypesDriver carClass, Pageable pageable);
    Page<VehicleEntity> findAllByYear(@Max(2030) @NotNull Integer year, Pageable pageable);
    Page<VehicleEntity> findAllByBrand(@NotBlank @Size(max = 50) String brand, Pageable pageable);
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
}
