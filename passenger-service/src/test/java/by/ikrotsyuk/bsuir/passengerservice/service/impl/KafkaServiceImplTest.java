package by.ikrotsyuk.bsuir.passengerservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KafkaServiceImplTest {
    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private KafkaServiceImpl kafkaService;

    private final PassengerEntity passengerEntity = TestDataGenerator.getPassengerEntity();
    private final RatingUpdatedEvent ratingUpdatedEvent = TestDataGenerator.getRatingUpdatedEvent();

    @Test
    void updateRating_ReturnsTrue() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.of(passengerEntity));
        when(passengerRepository.save(any(PassengerEntity.class)))
                .thenReturn(passengerEntity);

        var result = kafkaService.updateRating(ratingUpdatedEvent);

        assertThat(result)
                .isEqualTo(true);

        verify(passengerRepository)
                .findById(anyLong());
        verify(passengerRepository)
                .save(any(PassengerEntity.class));
    }

    @Test
    void updateRating_ReturnFalse() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        var result = kafkaService.updateRating(ratingUpdatedEvent);

        assertThat(result)
                .isEqualTo(false);

        verify(passengerRepository)
                .findById(anyLong());
    }
}
