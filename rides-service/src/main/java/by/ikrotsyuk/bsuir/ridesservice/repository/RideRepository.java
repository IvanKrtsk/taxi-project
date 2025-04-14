package by.ikrotsyuk.bsuir.ridesservice.repository;

import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<RideEntity, Long> {
    Page<RideEntity> findAllByPassengerId(Long passengerId, Pageable pageable);
    Page<RideEntity> findAllByRideStatus(RideStatusTypes rideStatusTypes, Pageable pageable);
    Page<RideEntity> findAllByDriverId(Long driverId, Pageable pageable);
    Optional<RideEntity> findByDriverIdAndRideStatus(Long driverId, RideStatusTypes rideStatusTypes);
}
