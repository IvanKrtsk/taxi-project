package by.ikrotsyuk.bsuir.driverservice.kafka.consumer.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RatingConsumerImpl {

    @KafkaListener(topics = "driver-rating-updated-event-topic")
    public void handleRatingChange(RatingUpdatedEvent event){
        System.out.println();
        System.err.println(event);
        System.out.println();
    }
}
