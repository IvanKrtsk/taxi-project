package by.ikrotsyuk.bsuir.ratingservice.exception.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
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
