package by.ikrotsyuk.bsuir.passengerservice.exception;

import by.ikrotsyuk.bsuir.passengerservice.exception.dto.ExceptionDTO;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.*;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PassengerNotFoundByIdException.class, PassengerNotFoundByEmailException.class, PassengersNotFoundException.class})
    public ResponseEntity<ExceptionDTO> handlePassengerNotFoundByIdException(ExceptionTemplate ex){
        String messageKey = ex.getMessageKey();
        String message = messageSource
                .getMessage(messageKey, ex.getArgs(), LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ExceptionDTO(message, messageKey), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PassengerWithSameEmailAlreadyExistsException.class, PassengerWithSamePhoneAlreadyExistsException.class, PassengerAlreadyDeletedException.class})
    public ResponseEntity<ExceptionDTO> handlePassengerWithSameEmailAlreadyExistsException(ExceptionTemplate ex){
        String messageKey = ex.getMessageKey();
        String message = messageSource
                .getMessage(ex.getMessageKey(), ex.getArgs(), LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ExceptionDTO(message, messageKey), HttpStatus.CONFLICT);
    }
}