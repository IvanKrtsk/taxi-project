package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.kafka;

public class NonRetryableException extends RuntimeException{
    public NonRetryableException(String message) {
        super(message);
    }

    public NonRetryableException(Throwable cause) {
        super(cause);
    }
}
