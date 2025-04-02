package by.ikrotsyuk.bsuir.passengerservice.repository;

import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
    Optional<PassengerEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
}