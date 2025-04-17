package by.ikrotsyuk.bsuir.ratingservice.config;

import by.ikrotsyuk.bsuir.ratingservice.kafka.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic createDriverRatingUpdatedTopic(){
        return TopicBuilder.name(KafkaConstants.DRIVER_RATING_TOPIC_NAME)
                .partitions(KafkaConstants.NUMBER_OF_PARTITIONS)
                .replicas(KafkaConstants.NUMBER_OF_REPLICAS)
                .configs(Map.of(KafkaConstants.NUMBER_OF_INSYNC_REPLICAS_KEY, KafkaConstants.NUMBER_OF_INSYNC_REPLICAS_VALUE))
                .build();
    }

    @Bean
    public NewTopic createPassengerRatingUpdatedTopic(){
        return TopicBuilder.name(KafkaConstants.PASSENGER_RATING_TOPIC_NAME)
                .partitions(KafkaConstants.NUMBER_OF_PARTITIONS)
                .replicas(KafkaConstants.NUMBER_OF_REPLICAS)
                .configs(Map.of(KafkaConstants.NUMBER_OF_INSYNC_REPLICAS_KEY, KafkaConstants.NUMBER_OF_INSYNC_REPLICAS_VALUE))
                .build();
    }
}
