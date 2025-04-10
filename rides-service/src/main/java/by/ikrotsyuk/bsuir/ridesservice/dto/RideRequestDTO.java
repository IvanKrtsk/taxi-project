package by.ikrotsyuk.bsuir.ridesservice.dto;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypesRides;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RideRequestDTO(
        @Schema(description = "pick up address")
        @NotBlank
        @Size(max = 255)
        String startLocation,

        @Schema(description = "destination address")
        @NotBlank
        @Size(max = 255)
        String endLocation,

        @Schema(description = "payment type(CASH/CARD)")
        @NotNull
        PaymentTypeTypesRides paymentType,

        @Schema(description = "car class")
        @NotNull
        CarClassTypesRides carClass
) {
}
