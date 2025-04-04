package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record RideFullRequestDTO(
        @Schema(description = "starting address")
        @NotBlank
        @Size(max = 255)
        String startLocation,

        @Schema(description = "destination address")
        @NotBlank
        @Size(max = 255)
        String endLocation,

        @Schema(description = "price of the trip")
        @NotNull
        BigDecimal cost,

        @Schema(description = "ride status")
        @NotNull
        RideStatusTypes rideStatus,

        @Schema(description = "payment type")
        @NotNull
        PaymentTypeTypes paymentType,

        @Schema(description = "trip rating")
        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("10.0")
        Double rating,

        @Schema(description = "estimated waiting time")
        @NotNull
        @Max(1000)
        Integer estimatedWaitingTime
) {
}
