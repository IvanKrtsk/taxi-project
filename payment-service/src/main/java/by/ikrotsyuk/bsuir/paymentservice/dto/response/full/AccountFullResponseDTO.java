package by.ikrotsyuk.bsuir.paymentservice.dto.response.full;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AccountFullResponseDTO(
        @Schema(description = "account id")
        Long id,

        @Schema(description = "account user id")
        Long userId,

        @Schema(description = "account type(DRIVER, PASSENGER)")
        AccountTypes accountType,

        @Schema(description = "current balance")
        BigDecimal balance,

        @Schema(description = "count of used promo-codes")
        Long usedPromocodesCount,

        @Schema(description = "selected payment type(CASH, CARD)")
        PaymentTypes selectedPaymentTypes,

        @Schema(description = "is account deleted")
        Boolean isDeleted,

        @Schema(description = "account creation date")
        OffsetDateTime createdAt,

        @Schema(description = "account last modified date")
        OffsetDateTime updatedAt
) {
}
