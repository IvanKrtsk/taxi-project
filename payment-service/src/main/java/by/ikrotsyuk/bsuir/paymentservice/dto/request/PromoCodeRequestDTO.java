package by.ikrotsyuk.bsuir.paymentservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record PromoCodeRequestDTO(
        @Schema(description = "promo code name")
        @NotBlank
        @NotNull
        String code,

        @Schema(description = "promo code discount percentage")
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        @NotNull
        Double discountPercentage,

        @Schema(description = "remaining number of promo code activations")
        @Min(1)
        @NotNull
        Long activationsCount,

        @Schema(description = "promo code start date")
        @NotNull
        OffsetDateTime startDate,

        @Schema(description = "promo code end date")
        @NotNull
        OffsetDateTime endDate,

        @Schema(description = "is promo code active")
        @NotNull
        Boolean isActive
) {
}
