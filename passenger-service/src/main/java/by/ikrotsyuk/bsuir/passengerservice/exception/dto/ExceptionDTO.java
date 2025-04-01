package by.ikrotsyuk.bsuir.passengerservice.exception.dto;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ExceptionDTO{
    private final String message;
    private final String messageKey;
    private final OffsetDateTime offsetDateTime;

    public ExceptionDTO(String message, String messageKey){
        this.message = message;
        this.messageKey = messageKey;
        this.offsetDateTime = OffsetDateTime.now();
    }
}
