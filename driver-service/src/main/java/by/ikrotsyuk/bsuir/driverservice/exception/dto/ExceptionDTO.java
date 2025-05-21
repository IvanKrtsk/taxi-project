package by.ikrotsyuk.bsuir.driverservice.exception.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionDTO {
    private String message;
    private String messageKey;
    private OffsetDateTime offsetDateTime;

    @JsonCreator
    public ExceptionDTO(@JsonProperty("message") String message,
                        @JsonProperty("messageKey") String messageKey) {
        this.message = message;
        this.messageKey = messageKey;
        this.offsetDateTime = OffsetDateTime.now();
    }
}