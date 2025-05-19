package by.ikrotsyuk.bsuir.paymentservice.dto.response;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentStatus;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record IncomePaymentResponseDTO(
        @Schema(description = "account id")
        Long accountId,

        @Schema(description = "ride id")
        Long rideId,

        @Schema(description = "driver id")
        Long driverId,

        @Schema(description = "ride cost")
        BigDecimal amount,

        @Schema(description = "payment type(CASH/CARD)")
        PaymentTypes paymentType,

        @Schema(description = "payment status(WAITING/PAID)")
        PaymentStatus paymentStatus
) {
}
