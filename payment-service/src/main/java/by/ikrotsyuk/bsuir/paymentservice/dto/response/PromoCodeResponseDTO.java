package by.ikrotsyuk.bsuir.paymentservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record PromoCodeResponseDTO(
        @Schema(description = "promo code name")
        String code,

        @Schema(description = "promo code discount percentage")
        Double discountPercentage,

        @Schema(description = "remaining number of promo code activations")
        Long activationsCount,

        @Schema(description = "promo code start date")
        Instant startDate,

        @Schema(description = "promo code end date")
        Instant endDate,

        @Schema(description = "is promo code active")
        Boolean isActive
) {
}
