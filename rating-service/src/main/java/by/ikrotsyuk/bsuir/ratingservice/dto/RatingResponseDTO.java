package by.ikrotsyuk.bsuir.ratingservice.dto;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import org.bson.types.ObjectId;

public record RatingResponseDTO(
        @Schema(description = "review id")
        @JsonSerialize(using = ToStringSerializer.class)
        ObjectId id,

        @Schema(description = "ride id")
        Long rideId,

        @Schema(description = "reviewer id")
        Long reviewerId,

        @Schema(description = "who left the review")
        ReviewerTypes reviewerType,

        @Schema(description = "rating")
        Double rating,

        @Schema(description = "review comment")
        String comment
) {
}
