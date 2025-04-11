package by.ikrotsyuk.bsuir.ridesservice.exceptions.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeneralExceptionMessageKeys {
    RIDE_NOT_FOUND_BY_ID_MESSAGE_KEY("ride.not.found.by.id.message"),
    RIDES_NOT_FOUND_MESSAGE_KEY("rides.not.found.message"),
    METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE_KEY("method.argument.type.mismatch.message"),
    ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY("enum.argument.deserialization.message"),
    FIELD_DESERIALIZATION_MESSAGE_KEY("field.deserialization.message");

    private final String messageKey;
}
