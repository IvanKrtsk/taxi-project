package by.ikrotsyuk.bsuir.ridesservice.repository;

import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<RideEntity, Long> {
    Page<RideEntity> findAllByPassengerId(Long passengerId, Pageable pageable);
}
