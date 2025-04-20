package by.ikrotsyuk.bsuir.driverservice.kafka.consumer.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.kafka.KafkaConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RatingConsumerImpl {

    @KafkaListener(topics = KafkaConstants.TOPIC_NAME)
    public void handleRatingChange(RatingUpdatedEvent event){
        System.err.println(event);

    }
}
