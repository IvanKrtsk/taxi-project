package by.ikrotsyuk.bsuir.ratingservice.dto;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypeTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RatingRequestDTO(
        @Schema(description = "id of the rated trip")
        @Min(1)
        @NotNull
        Long rideId,

        @Schema(description = "reviewer id")
        @Min(1)
        @NotNull
        Long reviewerId,

        @Schema(description = "who left the review")
        @NotNull
        ReviewerTypeTypes reviewerType,

        @Schema(description = "rating")
        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "10.0")
        Double rating,

        @Schema(description = "review comment")
        @Size(max = 1000)
        String comment
) {
}
