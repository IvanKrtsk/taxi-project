package by.ikrotsyuk.bsuir.driverservice.repository;

import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    Optional<DriverEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String email);
}
