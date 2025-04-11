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
        @NotBlank(message = "field.must.not.be.empty.message")
        @Size(max = 255, message = "field.must.have.size.message")
        String startLocation,

        @Schema(description = "destination address")
        @NotBlank(message = "field.must.not.be.empty.message")
        @Size(max = 255, message = "field.must.have.size.message")
        String endLocation,

        @Schema(description = "price of the trip")
        @NotNull(message = "field.must.not.be.empty.message")
        BigDecimal cost,

        @Schema(description = "ride status")
        @NotNull(message = "field.must.not.be.empty.message")
        RideStatusTypes rideStatus,

        @Schema(description = "payment type")
        @NotNull(message = "field.must.not.be.empty.message")
        PaymentTypeTypes paymentType,

        @Schema(description = "car class")
        @NotNull(message = "field.must.not.be.empty.message")
        CarClassTypes carClass,

        @Schema(description = "estimated waiting time")
        @NotNull(message = "field.must.not.be.empty.message")
        @Max(value = 1000, message = "field.must.have.size.message")
        Integer estimatedWaitingTime
) {
}
