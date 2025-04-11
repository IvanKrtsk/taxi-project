package by.ikrotsyuk.bsuir.driverservice.exception.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionTemplate extends RuntimeException {
    private final String messageKey;
    private final Object[] args;
}