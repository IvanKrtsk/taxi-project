package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import by.ikrotsyuk.bsuir.ridesservice.utils.ValidationExceptionMessageKeys;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RideFullRequestDTO(
        @Schema(description = "starting address")
        @NotBlank(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        @Size(max = 255, message = ValidationExceptionMessageKeys.FIELD_MUST_HAVE_SIZE_MESSAGE_KEY)
        String startLocation,

        @Schema(description = "destination address")
        @NotBlank(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        @Size(max = 255, message = ValidationExceptionMessageKeys.FIELD_MUST_HAVE_SIZE_MESSAGE_KEY)
        String endLocation,

        @Schema(description = "price of the trip")
        @NotNull(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        BigDecimal cost,

        @Schema(description = "ride status")
        @NotNull(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        RideStatusTypes rideStatus,

        @Schema(description = "payment type")
        @NotNull(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        PaymentTypeTypes paymentType,

        @Schema(description = "car class")
        @NotNull(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        CarClassTypes carClass,

        @Schema(description = "estimated waiting time")
        @NotNull(message = ValidationExceptionMessageKeys.FIELD_MUST_NOT_BE_EMPTY_MESSAGE_KEY)
        @Max(value = 1000, message = ValidationExceptionMessageKeys.FIELD_MUST_HAVE_SIZE_MESSAGE_KEY)
        Integer estimatedWaitingTime
) {
}
