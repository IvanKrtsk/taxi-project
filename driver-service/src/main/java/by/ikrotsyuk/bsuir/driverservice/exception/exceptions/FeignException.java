package by.ikrotsyuk.bsuir.driverservice.exception.exceptions;

import by.ikrotsyuk.bsuir.driverservice.exception.dto.ExceptionDTO;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {
    private final ExceptionDTO exceptionDTO;
    public FeignException(ExceptionDTO exceptionDTO) {
        super(exceptionDTO.getMessage());
        this.exceptionDTO = exceptionDTO;
    }
}
