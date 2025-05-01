package by.ikrotsyuk.bsuir.paymentservice.dto.response.full;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ExpensePaymentFullResponseDTO(
        @Schema(description = "expense payment id")
        ObjectId id,

        @Schema(description = "account id")
        Long accountId,

        @Schema(description = "amount")
        BigDecimal amount,

        @Schema(description = "expense payment creation date")
        OffsetDateTime createdAt,

        @Schema(description = "expense payment last modified date")
        OffsetDateTime updatedAt
) {
}
