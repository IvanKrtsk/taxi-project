package by.ikrotsyuk.bsuir.paymentservice.dto.response.full;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public record PromoCodeFullResponseDTO(
        @Schema(description = "promo code id")
        Long id,

        @Schema(description = "promo code name")
        String code,

        @Schema(description = "promo code discount percentage")
        Double discountPercentage,

        @Schema(description = "remaining number of promo code activations")
        Long activationsCount,

        @Schema(description = "promo code start date")
        OffsetDateTime startDate,

        @Schema(description = "promo code end date")
        OffsetDateTime endDate,

        @Schema(description = "is promo code active")
        Boolean isActive,

        @Schema(description = "promo code creation date")
        OffsetDateTime createdAt,

        @Schema(description = "promo code last modified date")
        OffsetDateTime updatedAt
) {
}
