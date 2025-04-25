package by.ikrotsyuk.bsuir.ratingservice.exception.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exception.dto.ExceptionDTO;
import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {
    private final ExceptionDTO exceptionDTO;
    public FeignException(ExceptionDTO exceptionDTO) {
        super(exceptionDTO.getMessage());
        this.exceptionDTO = exceptionDTO;
    }
}
