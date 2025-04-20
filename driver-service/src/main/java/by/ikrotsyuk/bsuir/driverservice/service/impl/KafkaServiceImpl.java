package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaServiceImpl {
    private final DriverRepository driverRepository;

    public boolean updateRating(RatingUpdatedEvent event){
        DriverEntity driverEntity = driverRepository.findById(event.reviewerId())
                .orElseThrow();
        return true;
    }
}
