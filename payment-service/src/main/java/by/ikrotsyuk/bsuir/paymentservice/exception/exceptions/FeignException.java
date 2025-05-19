package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions;

import by.ikrotsyuk.bsuir.paymentservice.exception.dto.ExceptionDTO;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {
    private final ExceptionDTO exceptionDTO;
    public FeignException(ExceptionDTO exceptionDTO) {
        super(exceptionDTO.getMessage());
        this.exceptionDTO = exceptionDTO;
    }
}
