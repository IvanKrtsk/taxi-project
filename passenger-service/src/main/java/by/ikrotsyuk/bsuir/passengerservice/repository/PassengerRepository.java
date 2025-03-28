package by.ikrotsyuk.bsuir.passengerservice.repository;

import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
}
