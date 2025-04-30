package by.ikrotsyuk.bsuir.passengerservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {
    private final PassengerRepository passengerRepository;

    @Override
    @Transactional
    public boolean updateRating(RatingUpdatedEvent event) {
        Optional<PassengerEntity> optionalPassengerEntity = passengerRepository.findById(event.reviewedId());
        if(optionalPassengerEntity.isEmpty())
            return false;
        PassengerEntity passengerEntity = optionalPassengerEntity.get();
        Long ridesCount = passengerEntity.getTotalRides();
        passengerEntity.setRating((passengerEntity.getRating() * ridesCount + event.rating()) / (++ridesCount));
        passengerEntity.setTotalRides(ridesCount);
        passengerRepository.save(passengerEntity);
        return true;
    }
}
