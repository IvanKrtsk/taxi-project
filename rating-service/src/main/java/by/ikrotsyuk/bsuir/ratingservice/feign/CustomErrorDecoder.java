package by.ikrotsyuk.bsuir.ratingservice.feign;

import by.ikrotsyuk.bsuir.ratingservice.exceptions.dto.ExceptionDTO;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.FeignDeserializationException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.FeignException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.body() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());

                String responseBody = readResponseBody(response);
                if (isExceptionDTO(responseBody, objectMapper)) {
                    ExceptionDTO exceptionDTO = objectMapper.readValue(responseBody, ExceptionDTO.class);
                    throw new FeignException(exceptionDTO);
                }
            }
            throw new RuntimeException();
        } catch (Exception e) {
            throw new FeignDeserializationException();
        }
    }

    private boolean isExceptionDTO(String responseBody, ObjectMapper objectMapper) {
        try {
            objectMapper.readValue(responseBody, ExceptionDTO.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SneakyThrows
    private String readResponseBody(Response response) {
        return response.body() != null
                ? new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8)
                : "";
    }
}

