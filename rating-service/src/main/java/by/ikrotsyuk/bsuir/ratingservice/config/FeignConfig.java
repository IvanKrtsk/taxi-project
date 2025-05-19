package by.ikrotsyuk.bsuir.ratingservice.config;

import by.ikrotsyuk.bsuir.ratingservice.feign.ApiExceptionErrorDecoder;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FeignConfig {
    public static final Long RETRY_PERIOD = 100L;
    public static final int RETRY_MAX_ATTEMPTS = 3;

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(RETRY_PERIOD, SECONDS.toMillis(1), RETRY_MAX_ATTEMPTS);
    }

    @Bean
    public ApiExceptionErrorDecoder apiExceptionErrorDecoder() {
        return new ApiExceptionErrorDecoder();
    }
}