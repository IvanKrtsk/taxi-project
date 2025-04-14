package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record RideFullResponseDTO(
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
        RideStatusTypes rideStatus,

        @Schema(description = "payment type")
        PaymentTypeTypes paymentType,

        @Schema(description = "car class")
        CarClassTypes carClass,

        @Schema(description = "taxi call time")
        OffsetDateTime bookedAt,

        @Schema(description = "estimated waiting time")
        Integer estimatedWaitingTime,

        @Schema(description = "taxi driver's acceptance time")
        OffsetDateTime acceptedAt,

        @Schema(description = "trip start time")
        OffsetDateTime beganAt,

        @Schema(description = "trip end time")
        OffsetDateTime endedAt
) {
}
