package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KafkaServiceImplTest {
    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private KafkaServiceImpl kafkaServiceImpl;

    private RatingUpdatedEvent ratingUpdatedEvent;

    private DriverEntity driverEntity;

    @BeforeEach
    void setUp(){
        ratingUpdatedEvent = TestDataGenerator.getRatingUpdatedEvent();
        driverEntity = TestDataGenerator.getDriverEntity();
    }

    @Test
    void updateRating_ReturnsFalse(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        var result = kafkaServiceImpl.updateRating(ratingUpdatedEvent);

        assertThat(result)
                .isEqualTo(false);

        verify(driverRepository)
                .findById(anyLong());
    }

    @Test
    void updateRating_ReturnsTrue(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));

        var result = kafkaServiceImpl.updateRating(ratingUpdatedEvent);

        assertThat(result)
                .isEqualTo(true);

        verify(driverRepository)
                .findById(anyLong());
    }
}
