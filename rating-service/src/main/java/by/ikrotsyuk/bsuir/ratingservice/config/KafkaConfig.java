package by.ikrotsyuk.bsuir.ratingservice.config;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.ratingservice.kafka.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    Map<String, Object> producerConfigs(){
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_SERVERS);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaConstants.KEY_SERIALIZER);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaConstants.VALUE_SERIALIZER);
        config.put(ProducerConfig.ACKS_CONFIG, KafkaConstants.ACKNOWLEDGEMENTS_POLICY);
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, KafkaConstants.DELIVERY_TIMEOUT_MS);
        config.put(ProducerConfig.LINGER_MS_CONFIG, KafkaConstants.LINGER_MS);
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, KafkaConstants.REQUEST_TIMEOUT_MS);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, KafkaConstants.ENABLE_IDEMPOTENCE);
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, KafkaConstants.MAX_IN_FLIGHT_REQUESTS_PER_SECOND);

        return config;
    }

    @Bean
    ProducerFactory<String, RatingUpdatedEvent> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    KafkaTemplate<String, RatingUpdatedEvent> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

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
