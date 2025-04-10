package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypesRides;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record RideResponseDTO(
        @Schema(description = "ride id")
        Long id,

        @Schema(description = "trip passenger id")
        Long passengerId,

        @Schema(description = "trip driver id")
        Long driverId,

        @Schema(description = "starting address")
        String startLocation,

        @Schema(description = "destination address")
        String endLocation,

        @Schema(description = "price of the trip")
        BigDecimal cost,

        @Schema(description = "ride status")
        RideStatusTypesRides rideStatus,

        @Schema(description = "payment type")
        PaymentTypeTypesRides paymentType,

        @Schema(description = "taxi call time")
        OffsetDateTime bookedAt,

        @Schema(description = "estimated waiting time")
        Integer estimatedWaitingTime
) {
}