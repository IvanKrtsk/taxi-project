package by.ikrotsyuk.bsuir.paymentservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ExpensePaymentResponseDTO(
        @Schema(description = "account id")
        Long accountId,

        @Schema(description = "amount")
        BigDecimal amount
) {
}
