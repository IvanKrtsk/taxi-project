package by.ikrotsyuk.bsuir.ratingservice.kafka;

public final class KafkaConstants {
    public static final String DRIVER_RATING_TOPIC_NAME = "driver-rating-updated-event-topic";
    public static final String PASSENGER_RATING_TOPIC_NAME = "passenger-rating-updated-event-topic";
    public static final int NUMBER_OF_PARTITIONS = 3;
    public static final int NUMBER_OF_REPLICAS = 3;
    public static final String NUMBER_OF_INSYNC_REPLICAS_KEY = "min.insync.replicas";
    public static final String NUMBER_OF_INSYNC_REPLICAS_VALUE = "2";
}
