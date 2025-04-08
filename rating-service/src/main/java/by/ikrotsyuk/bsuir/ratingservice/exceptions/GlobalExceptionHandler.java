package by.ikrotsyuk.bsuir.ratingservice.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exceptions.dto.ExceptionDTO;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.IdIsNotValidException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.ReviewNotFoundByIdException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.ReviewsNotFoundException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private final String DTO_PACKAGE_PATH = "by.ikrotsyuk.bsuir.ratingservice.dto.";

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();
                Object rejectedValue = fieldError.getRejectedValue();
                String errorMessage;
                try {
                    String fullQualifiedName = DTO_PACKAGE_PATH + capitalize(fieldError.getObjectName());

                    Class<?> dtoClass = Class.forName(fullQualifiedName);
                    Field field = dtoClass.getDeclaredField(fieldName);
                    Class<?> fieldType = field.getType();

                    if (fieldType.isEnum()) {
                        Object[] enumValues = fieldType.getEnumConstants();
                        String possibleValues = Arrays.toString(enumValues);
                        errorMessage = messageSource.getMessage(GeneralExceptionMessageKeys.ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY.getMessageKey(),
                                new Object[]{fieldName, rejectedValue, possibleValues},
                                LocaleContextHolder.getLocale());
                    } else {
                        errorMessage = fieldError.getDefaultMessage();
                    }
                } catch (ClassNotFoundException | NoSuchFieldException e) {
                    errorMessage = messageSource.getMessage(GeneralExceptionMessageKeys.FIELD_DESERIALIZATION_MESSAGE_KEY.getMessageKey(),
                            new Object[]{fieldError.getRejectedValue()}, LocaleContextHolder.getLocale());
                }
                errors.put(fieldName, errorMessage);
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();

        String rejectedValue;
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormatException) {
            Class<?> targetType = invalidFormatException.getTargetType();
            rejectedValue = invalidFormatException.getValue().toString();
            String fieldName = invalidFormatException.getPath().get(0).getFieldName();
            if (targetType.isEnum()) {
                Object[] enumValues = targetType.getEnumConstants();
                String possibleValues = Arrays.toString(enumValues);

                String errorMessage = messageSource.getMessage(GeneralExceptionMessageKeys.ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY.getMessageKey(),
                        new Object[]{fieldName, rejectedValue, possibleValues},
                        LocaleContextHolder.getLocale());

                errors.put(fieldName, errorMessage);
            } else {
                errors.put(fieldName, messageSource.getMessage(GeneralExceptionMessageKeys.FIELD_DESERIALIZATION_MESSAGE_KEY.getMessageKey(),
                        new Object[]{rejectedValue}, LocaleContextHolder.getLocale()));
            }
        } else {
            errors.put("error", ex.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    private String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    @ExceptionHandler({ReviewNotFoundByIdException.class, ReviewsNotFoundException.class})
    public ResponseEntity<ExceptionDTO> handleRideNotFoundException(ExceptionTemplate ex){
        String messageKey = ex.getMessageKey();
        String message = messageSource
                .getMessage(messageKey, ex.getArgs(), LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ExceptionDTO(message, messageKey), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IdIsNotValidException.class})
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
}