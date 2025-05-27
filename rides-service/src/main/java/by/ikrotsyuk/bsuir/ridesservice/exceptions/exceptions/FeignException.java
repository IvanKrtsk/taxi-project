package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.dto.ExceptionDTO;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {
    private final ExceptionDTO exceptionDTO;
    public FeignException(ExceptionDTO exceptionDTO) {
        super(exceptionDTO.getMessage());
        this.exceptionDTO = exceptionDTO;
    }
}
