package by.ikrotsyuk.bsuir.ratingservice.dto;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import org.bson.types.ObjectId;

import java.util.Date;

public record RatingAdminResponseDTO(
        @Schema(description = "review id")
        @JsonSerialize(using = ToStringSerializer.class)
        ObjectId id,

        @Schema(description = "ride id")
        Long rideId,

        @Schema(description = "reviewer id")
        Long reviewerId,

        @Schema(description = "who left the review")
        ReviewerTypesRating reviewerType,

        @Schema(description = "rating")
        Double rating,

        @Schema(description = "review comment")
        String comment,

        @Schema(description = "created at time")
        Date createdAt,

        @Schema(description = "updated at time")
        Date updatedAt
) {
}
