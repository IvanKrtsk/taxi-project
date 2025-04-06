package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypesRides;
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
        RideStatusTypesRides rideStatus,

        @Schema(description = "payment type")
        @NotNull
        PaymentTypeTypesRides paymentType,

        @Schema(description = "car class")
        @NotNull
        CarClassTypesRides carClass,

        @Schema(description = "estimated waiting time")
        @NotNull
        @Max(1000)
        Integer estimatedWaitingTime
) {
}
