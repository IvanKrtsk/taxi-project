package by.ikrotsyuk.bsuir.driverservice.kafka.consumer.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.kafka.NonRetryableException;
import by.ikrotsyuk.bsuir.driverservice.service.impl.KafkaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingConsumerImplTest {
    @Mock
    private KafkaServiceImpl kafkaServiceImpl;

    @InjectMocks
    private RatingConsumerImpl ratingConsumerImpl;

    private RatingUpdatedEvent ratingUpdatedEvent;

    @BeforeEach
    void setUp(){
        ratingUpdatedEvent = TestDataGenerator.getRatingUpdatedEvent();
    }

    @Test
    void handleRatingChange_ThrowsNonRetryableException(){
        when(kafkaServiceImpl.updateRating(ratingUpdatedEvent))
                .thenReturn(false);

        assertThatThrownBy(() ->
                ratingConsumerImpl.handleRatingChange(ratingUpdatedEvent)
        ).isInstanceOf(NonRetryableException.class);

        verify(kafkaServiceImpl)
                .updateRating(ratingUpdatedEvent);
    }

    @Test
    void handleRatingChange_Success(){
        when(kafkaServiceImpl.updateRating(ratingUpdatedEvent))
                .thenReturn(true);

        assertDoesNotThrow(() ->
                ratingConsumerImpl.handleRatingChange(ratingUpdatedEvent)
        );

        verify(kafkaServiceImpl)
                .updateRating(ratingUpdatedEvent);
    }

}
