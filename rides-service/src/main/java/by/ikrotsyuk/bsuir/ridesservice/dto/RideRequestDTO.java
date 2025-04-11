package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RideRequestDTO(
        @Schema(description = "pick up address")
        @NotBlank(message = "field.must.not.be.empty.message")
        @Size(max = 255, message = "field.must.have.size.message")
        String startLocation,

        @Schema(description = "destination address")
        @NotBlank(message = "field.must.not.be.empty.message")
        @Size(max = 255, message = "field.must.have.size.message")
        String endLocation,

        @Schema(description = "payment type(CASH/CARD)")
        @NotNull(message = "field.must.not.be.empty.message")
        PaymentTypeTypes paymentType,

        @Schema(description = "car class")
        @NotNull(message = "field.must.not.be.empty.message")
        CarClassTypes carClass
) {
}
