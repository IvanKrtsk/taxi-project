package by.ikrotsyuk.bsuir.ridesservice.exceptions.template;

import lombok.Getter;

@Getter
public class ExceptionTemplate extends RuntimeException {
    private final String messageKey;
    private final Object[] args;

    public ExceptionTemplate(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }
}
