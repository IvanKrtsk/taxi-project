package by.ikrotsyuk.bsuir.ratingservice.dto;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import io.swagger.v3.oas.annotations.media.Schema;

public record RatingResponseDTO(
        @Schema(description = "review id")
        Long id,

        @Schema(description = "ride id")
        Long rideId,

        @Schema(description = "reviewer id")
        Long reviewerId,

        @Schema(description = "who left the review")
        ReviewerTypesRating reviewer,

        @Schema(description = "rating")
        Double rating,

        @Schema(description = "review comment")
        String comment
) {
}
