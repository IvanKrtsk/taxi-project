package by.ikrotsyuk.bsuir.ratingservice.feign;

import by.ikrotsyuk.bsuir.ratingservice.exception.dto.ExceptionDTO;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.FeignDeserializationException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.FeignException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder.Default defaultError = new ErrorDecoder.Default();

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ExceptionDTO exceptionDTO;
        if (response.status() >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return defaultError.decode(s, response);
        }

        try {
            exceptionDTO = objectMapper.readValue(readResponseBody(response), ExceptionDTO.class);
        } catch (JsonProcessingException e) {
            throw new FeignDeserializationException();
        }
        throw new FeignException(exceptionDTO);
    }

    @SneakyThrows
    private String readResponseBody(Response response) {
        if (Objects.nonNull(response.body())) {
            @Cleanup InputStreamReader inputStreamReader =
                    new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8);
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
        return "";
    }
}

