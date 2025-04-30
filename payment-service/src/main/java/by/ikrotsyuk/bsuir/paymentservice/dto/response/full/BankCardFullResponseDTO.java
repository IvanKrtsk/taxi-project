package by.ikrotsyuk.bsuir.paymentservice.dto.response.full;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record BankCardFullResponseDTO(
        @Schema(description = "bank card id")
        Long id,

        @Schema(description = "card owner's account id")
        Long accountId,

        @Schema(description = "card number")
        String cardNumber,

        @Schema(description = "card expiration date")
        String expirationDate,

        @Schema(description = "is card chosen")
        Boolean isChosen,

        @Schema(description = "card creation date")
        LocalDateTime createdAt,

        @Schema(description = "card last modified date")
        LocalDateTime updatedAt
) {
}
