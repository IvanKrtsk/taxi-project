package by.ikrotsyuk.bsuir.driverservice.kafka;

public final class KafkaConstants {
    public static final String BOOTSTRAP_SERVERS = "localhost:9092, localhost:9094";
    public static final String CONSUMER_GROUP_ID = "driver-rating-updated";
    public static final String TRUSTED_PACKAGES = "*";
    public static final String TOPIC_NAME = "driver-rating-updated-event-topic";
    public static final String NON_RETRYABLE_EXCEPTION_TEXT = "Nonretryable exception";
}
