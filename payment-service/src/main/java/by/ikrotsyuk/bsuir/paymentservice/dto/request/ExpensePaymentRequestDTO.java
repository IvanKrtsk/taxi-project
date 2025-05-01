package by.ikrotsyuk.bsuir.paymentservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExpensePaymentRequestDTO(
        @Schema(description = "account id")
        @Min(1)
        @NotNull
        Long accountId,

        @Schema(description = "amount")
        @DecimalMin("0.0")
        @NotNull
        BigDecimal amount
) {
}
