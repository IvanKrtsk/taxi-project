package by.ikrotsyuk.bsuir.paymentservice.dto.request;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IncomePaymentRequestDTO(
        @Schema(description = "account id")
        @Min(1)
        @NotNull
        Long accountId,

        @Schema(description = "ride id")
        @Min(1)
        @NotNull
        Long rideId,

        @Schema(description = "ride id")
        @Min(1)
        @NotNull
        Long driverId,

        @Schema(description = "ride cost")
        @DecimalMin("0.0")
        @NotNull
        BigDecimal amount,

        @Schema(description = "payment type(CASH, CARD)")
        @NotNull
        PaymentTypes paymentTypes
) {
}
