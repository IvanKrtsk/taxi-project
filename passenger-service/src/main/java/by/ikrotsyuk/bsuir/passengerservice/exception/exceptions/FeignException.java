package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.dto.ExceptionDTO;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {
    private final ExceptionDTO exceptionDTO;
    public FeignException(ExceptionDTO exceptionDTO) {
        super(exceptionDTO.getMessage());
        this.exceptionDTO = exceptionDTO;
    }
}
