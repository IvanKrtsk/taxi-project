package by.ikrotsyuk.bsuir.ratingservice.dto;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RatingRequestDTO(
        @Schema(description = "id of the rated trip")
        @Min(value = 1, message = "field.must.have.size.message")
        @NotNull(message = "field.must.not.be.empty.message")
        Long rideId,

        @Schema(description = "reviewer id")
        @Min(value = 1, message = "field.must.have.size.message")
        @NotNull(message = "field.must.not.be.empty.message")
        Long reviewerId,

        @Schema(description = "reviewed id")
        @Min(value = 1, message = "field.must.have.size.message")
        @NotNull(message = "field.must.not.be.empty.message")
        Long reviewedId,

        @Schema(description = "who left the review")
        @NotNull(message = "field.must.not.be.empty.message")
        ReviewerTypes reviewerType,

        @Schema(description = "rating")
        @NotNull(message = "field.must.not.be.empty.message")
        @DecimalMin(value = "0.0", message = "field.must.have.size.message")
        @DecimalMax(value = "10.0", message = "field.must.have.size.message")
        Double rating,

        @Schema(description = "review comment")
        @Size(max = 1000, message = "field.must.have.size.message")
        String comment
) {
}
