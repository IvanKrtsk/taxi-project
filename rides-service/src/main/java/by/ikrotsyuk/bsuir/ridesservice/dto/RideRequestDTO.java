package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ridesservice.utils.ValidationExceptionMessageKeys;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RideRequestDTO(
        @Schema(description = "pick up address")
        @NotBlank(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        @Size(max = 255, message = ValidationExceptionMessageKeys.FIELD_MUST_HAVE_SIZE_MESSAGE_KEY)
        String startLocation,

        @Schema(description = "destination address")
        @NotBlank(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        @Size(max = 255, message = ValidationExceptionMessageKeys.FIELD_MUST_HAVE_SIZE_MESSAGE_KEY)
        String endLocation,

        @Schema(description = "payment type(CASH/CARD)")
        @NotNull(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        PaymentTypeTypes paymentType,

        @Schema(description = "car class")
        @NotNull(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        CarClassTypes carClass
) {
}
