package by.ikrotsyuk.bsuir.driverservice.exception;

import by.ikrotsyuk.bsuir.driverservice.exception.dto.ExceptionDTO;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.*;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.*;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;
    private final String DTO_PACKAGE_PATH = "by.ikrotsyuk.bsuir.driverservice.dto.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) {
                String errorMessage = generateFieldErrorMessage(fieldError);
                errors.put(fieldError.getField(), errorMessage);
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            String errorMessage = handleInvalidFormatException(invalidFormatException);
            String fieldName = invalidFormatException.getPath().get(0).getFieldName();
            errors.put(fieldName, errorMessage);
        } else {
            errors.put("error", ex.getMessage());
        }
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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String messageKey = GeneralExceptionMessageKeys.METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE_KEY.getMessageKey();
        String message = messageSource
                .getMessage(messageKey, new Object[]{ex.getName(), ex.getRequiredType(), ex.getValue()}, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ExceptionDTO(message, messageKey), HttpStatus.BAD_REQUEST);
    }

    private String generateFieldErrorMessage(FieldError fieldError) {
        String fieldName = fieldError.getField();
        Object rejectedValue = fieldError.getRejectedValue();
        try {
            String fullQualifiedName = DTO_PACKAGE_PATH + capitalize(fieldError.getObjectName());
            Class<?> dtoClass = Class.forName(fullQualifiedName);
            Field field = dtoClass.getDeclaredField(fieldName);
            Class<?> fieldType = field.getType();

            if (fieldType.isEnum()) {
                return generateEnumErrorMessage(fieldType, fieldName, rejectedValue);
            } else {
                return fieldError.getDefaultMessage();
            }
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            return messageSource.getMessage(
                    GeneralExceptionMessageKeys.FIELD_DESERIALIZATION_MESSAGE_KEY.getMessageKey(),
                    new Object[]{rejectedValue},
                    LocaleContextHolder.getLocale()
            );
        }
    }

    private String handleInvalidFormatException(InvalidFormatException ex) {
        String fieldName = ex.getPath().get(0).getFieldName();
        Object rejectedValue = ex.getValue();
        Class<?> targetType = ex.getTargetType();

        if (targetType.isEnum()) {
            return generateEnumErrorMessage(targetType, fieldName, rejectedValue);
        } else {
            return messageSource.getMessage(
                    GeneralExceptionMessageKeys.FIELD_DESERIALIZATION_MESSAGE_KEY.getMessageKey(),
                    new Object[]{rejectedValue},
                    LocaleContextHolder.getLocale()
            );
        }
    }

    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private String generateEnumErrorMessage(Class<?> fieldType, String fieldName, Object rejectedValue) {
        Object[] enumValues = fieldType.getEnumConstants();
        String possibleValues = Arrays.toString(enumValues);
        return messageSource.getMessage(
                GeneralExceptionMessageKeys.ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY.getMessageKey(),
                new Object[]{fieldName, rejectedValue, possibleValues},
                LocaleContextHolder.getLocale()
        );
    }
}