package by.ikrotsyuk.bsuir.paymentservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BankCardRequestDTO(
        @Schema(description = "account id")
        @Min(1)
        @NotNull
        Long accountId,

        @Schema(description = "card number")
        @Pattern(regexp = "\\d{16}")
        @NotBlank
        @NotNull
        String cardNumber,

        @Schema(description = "card expiration date")
        @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}")
        @NotBlank
        @NotNull
        String expirationDate,

        @Schema(description = "is card chosen")
        @NotNull
        Boolean isChosen
) {
}
