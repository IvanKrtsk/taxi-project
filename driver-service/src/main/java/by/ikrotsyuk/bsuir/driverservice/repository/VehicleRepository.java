package by.ikrotsyuk.bsuir.driverservice.repository;

import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
