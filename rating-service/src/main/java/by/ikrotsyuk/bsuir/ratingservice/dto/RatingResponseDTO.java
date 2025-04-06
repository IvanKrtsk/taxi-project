package by.ikrotsyuk.bsuir.ratingservice.dto;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import io.swagger.v3.oas.annotations.media.Schema;
import org.bson.types.ObjectId;

public record RatingResponseDTO(
        @Schema(description = "review id")
        ObjectId id,

        @Schema(description = "id of the rated trip")
        Long rideId,

        @Schema(description = "passenger ig")
        Long passengerId,

        @Schema(description = "driver id")
        Long driverId,

        @Schema(description = "who left the review")
        ReviewerTypesRating reviewer,

        @Schema(description = "rating")
        Double rating,

        @Schema(description = "review comment")
        String comment
) {
}
