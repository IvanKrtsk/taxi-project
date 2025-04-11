package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

        @Schema(description = "car class")
        @NotNull
        CarClassTypes carClass,

        @Schema(description = "estimated waiting time")
        @NotNull
        @Max(1000)
        Integer estimatedWaitingTime
) {
}
