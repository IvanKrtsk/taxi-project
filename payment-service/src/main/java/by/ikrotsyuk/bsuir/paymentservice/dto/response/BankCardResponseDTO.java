package by.ikrotsyuk.bsuir.paymentservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record BankCardResponseDTO(
        @Schema(description = "card owner's account id")
        Long accountId,

        @Schema(description = "card number")
        String cardNumber,

        @Schema(description = "card expiration date")
        String expirationDate,

        @Schema(description = "is card chosen")
        Boolean isChosen
) {
}
