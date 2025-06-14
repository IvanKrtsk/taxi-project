package by.ikrotsyuk.bsuir.driverservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeneralExceptionMessageKeys {
    ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY("enum.argument.deserialization.message"),
    METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE_KEY("method.argument.type.mismatch.message"),
    FIELD_DESERIALIZATION_MESSAGE_KEY("field.deserialization.message"),
    FEIGN_DESERIALIZATION_MESSAGE_KEY("feign.deserialization.message"),
    FEIGN_CONNECT_MESSAGE_KEY("feign.connection.message.key");

    private final String messageKey;
}
