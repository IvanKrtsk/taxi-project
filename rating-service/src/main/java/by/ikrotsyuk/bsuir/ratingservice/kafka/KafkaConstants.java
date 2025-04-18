package by.ikrotsyuk.bsuir.ratingservice.kafka;

public final class KafkaConstants {
    public static final String DRIVER_RATING_TOPIC_NAME = "driver-rating-updated-event-topic";
    public static final String PASSENGER_RATING_TOPIC_NAME = "passenger-rating-updated-event-topic";
    public static final int NUMBER_OF_PARTITIONS = 3;
    public static final int NUMBER_OF_REPLICAS = 3;
    public static final String NUMBER_OF_INSYNC_REPLICAS_KEY = "min.insync.replicas";
    public static final String NUMBER_OF_INSYNC_REPLICAS_VALUE = "2";
    public static final String BOOTSTRAP_SERVERS = "localhost:9092, localhost:9094";
    public static final String KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String VALUE_SERIALIZER = "org.springframework.kafka.support.serializer.JsonSerializer";
    public static final String ACKNOWLEDGEMENTS_POLICY = "all";
    public static final String DELIVERY_TIMEOUT_MS = "100000";
    public static final String LINGER_MS = "0";
    public static final String REQUEST_TIMEOUT_MS = "30000";
    public static final String ENABLE_IDEMPOTENCE = "true";
    public static final String MAX_IN_FLIGHT_REQUESTS_PER_SECOND = "5";
}
