package by.ikrotsyuk.bsuir.driverservice.exception;

import by.ikrotsyuk.bsuir.driverservice.exception.dto.ExceptionDTO;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.*;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.*;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
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

    @ExceptionHandler({DriverNotFoundByEmailException.class, DriverNotFoundByIdException.class,
            DriversNotFoundException.class, DriverVehiclesNotFoundException.class,
            DriverCurrentVehicleNotFoundException.class, VehiclesNotFoundByBrandException.class,
            VehicleNotFoundByIdException.class, VehicleNotFoundByLicensePlateException.class,
            VehiclesNotFoundByYearException.class, VehiclesNotFoundByTypeException.class})
    public ResponseEntity<ExceptionDTO> handleNotFoundExceptions(ExceptionTemplate ex){
        String messageKey = ex.getMessageKey();
        String message = messageSource
                .getMessage(messageKey, ex.getArgs(), LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ExceptionDTO(message, messageKey), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DriverWithSameEmailAlreadyExistsException.class, DriverWithSamePhoneAlreadyExistsException.class, DriverAlreadyDeletedException.class, VehicleNotBelongToDriverException.class, VehicleWithSameLicensePlateAlreadyExistsException.class})
    public ResponseEntity<ExceptionDTO> handleConflictExceptions(ExceptionTemplate ex){
        String messageKey = ex.getMessageKey();
        String message = messageSource
                .getMessage(messageKey, ex.getArgs(), LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ExceptionDTO(message, messageKey), HttpStatus.CONFLICT);
    }
}