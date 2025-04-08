package by.ikrotsyuk.bsuir.passengerservice.exception.keys;

import lombok.Getter;

@Getter
public enum GeneralExceptionMessageKeys {
    ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY("enum.argument.deserialization.message"),
    METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE_KEY("method.argument.type.mismatch.message"),
    FIELD_DESERIALIZATION_MESSAGE_KEY("field.deserialization.message");

    private final String messageKey;

    GeneralExceptionMessageKeys(String messageKey){
        this.messageKey = messageKey;
    }
}
