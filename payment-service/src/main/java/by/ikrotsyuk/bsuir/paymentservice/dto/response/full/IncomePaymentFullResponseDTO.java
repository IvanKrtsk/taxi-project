package by.ikrotsyuk.bsuir.paymentservice.dto.response.full;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentStatus;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record IncomePaymentFullResponseDTO(
        @Schema(description = "income payment id")
        ObjectId id,

        @Schema(description = "account id")
        Long accountId,

        @Schema(description = "ride id")
        Long rideId,

        @Schema(description = "driver id")
        Long driverId,

        @Schema(description = "ride cost")
        BigDecimal amount,

        @Schema(description = "payment type(CASH, CARD)")
        PaymentTypes paymentTypes,

        @Schema(description = "payment status(WAITING/PAID)")
        PaymentStatus paymentStatus,

        @Schema(description = "income payment creation time")
        OffsetDateTime createdAt,

        @Schema(description = "income payment last modified date")
        OffsetDateTime modifiedAt
) {
}
