package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {
    private final DriverRepository driverRepository;

    @Transactional
    public boolean updateRating(RatingUpdatedEvent event){
        Optional<DriverEntity> optionalDriverEntity = driverRepository.findById(event.reviewedId());
        if(optionalDriverEntity.isEmpty())
            return false;
        DriverEntity driverEntity = optionalDriverEntity.get();
        Long ridesCount = driverEntity.getTotalRides();
        driverEntity.setRating((driverEntity.getRating() * ridesCount + event.rating()) / (ridesCount + 1));
        driverEntity.setTotalRides(ridesCount + 1);
        driverRepository.save(driverEntity);
        return true;
    }
}
