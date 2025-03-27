package by.ikrotsyuk.bsuir.driverservice.repository;

import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
}
