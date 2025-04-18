package by.ikrotsyuk.bsuir.driverservice.kafka;

public final class KafkaConstants {
    public static final String BOOTSTRAP_SERVERS = "localhost:9092, localhost:9094";
    public static final String CONSUMER_GROUP_ID = "driver-rating-updated";
    public static final String TRUSTED_PACKAGES = "by.ikrotsyuk.bsuir.driverservice.event";
    public static final String JSON_VALUE_DEFAULT_TYPE = "by.ikrotsyuk.bsuir.driverservice.event.RatingUpdatedEvent";
}
