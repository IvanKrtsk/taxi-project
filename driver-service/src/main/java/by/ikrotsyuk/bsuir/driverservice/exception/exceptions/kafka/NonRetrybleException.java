package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.kafka;

public class NonRetrybleException extends RuntimeException{
    public NonRetrybleException(String message) {
        super(message);
    }

    public NonRetrybleException(Throwable cause) {
        super(cause);
    }
}
