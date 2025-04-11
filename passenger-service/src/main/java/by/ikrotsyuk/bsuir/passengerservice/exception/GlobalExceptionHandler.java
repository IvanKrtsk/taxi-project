package by.ikrotsyuk.bsuir.passengerservice.exception;

import by.ikrotsyuk.bsuir.passengerservice.exception.dto.ExceptionDTO;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.*;
import by.ikrotsyuk.bsuir.passengerservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String messageKey = GeneralExceptionMessageKeys.ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY.getMessageKey();
        String message = messageSource.getMessage(messageKey, new Object[]{ex.getParameterName(), ex.getMethodParameter(), ex.getParameterType()}, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ExceptionDTO(message, messageKey), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    private String handleInvalidFormatException(InvalidFormatException ex) {
        String fieldName = ex.getPath().getFirst().getFieldName();
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        AtomicReference<ExceptionDTO> exceptionDTO = new AtomicReference<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            String messageKey = error.getDefaultMessage();
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();
                Object invalidValue = fieldError.getRejectedValue();
                try{
                    errorMessage = messageSource.getMessage(messageKey, new Object[]{fieldName, invalidValue}, LocaleContextHolder.getLocale());
                }catch(NoSuchMessageException exception){
                    errorMessage = generateFieldErrorMessage(fieldError);
                }finally {
                    exceptionDTO.set(new ExceptionDTO(errorMessage, messageKey));
                }
            }
        });
        return new ResponseEntity<>(exceptionDTO.get(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDTO> handleConstraintViolation(ConstraintViolationException ex) {
        AtomicReference<ExceptionDTO> exceptionDTO = new AtomicReference<>();
        ex.getConstraintViolations().forEach(violation -> {
            String messageKey = violation.getMessageTemplate();
            String fieldName = violation.getPropertyPath().toString();
            Object invalidValue = violation.getInvalidValue();
            String errorMessage = null;
            try{
                errorMessage = messageSource.getMessage(messageKey, new Object[]{fieldName, invalidValue}, LocaleContextHolder.getLocale());
            }catch (NoSuchMessageException exception){
                errorMessage = violation.getMessage();
            }finally {
                exceptionDTO.set(new ExceptionDTO(errorMessage, messageKey));
            }
        });
        return new ResponseEntity<>(exceptionDTO.get(), HttpStatus.BAD_REQUEST);
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
            String DTO_PACKAGE_PATH = "by.ikrotsyuk.bsuir.passengerservice.dto.";
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