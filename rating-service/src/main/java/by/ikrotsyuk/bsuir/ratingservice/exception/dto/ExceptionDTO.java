package by.ikrotsyuk.bsuir.ratingservice.exception.dto;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ExceptionDTO{
    private final String message;
    private final String messageKey;
    private final OffsetDateTime offsetDateTime = OffsetDateTime.now();

    public ExceptionDTO(String message, String messageKey){
        this.message = message;
        this.messageKey = messageKey;
    }

    public ExceptionDTO() {
        this.message = "";
        this.messageKey = "";
    }
}
