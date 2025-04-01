package by.ikrotsyuk.bsuir.ratingservice.entity;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.OffsetDateTime;

@Document(collection = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingEntity {
    @Schema(description = "review id")
    @Id
    private ObjectId id;
    @Schema(description = "id of the rated trip")
    @NotNull
    private Long rideId;
    @Schema(description = "passenger ig")
    @NotNull
    private Long passengerId;
    @Schema(description = "driver id")
    @NotNull
    private Long driverId;
    @Schema(description = "who left the review")
    @NotNull
    private ReviewerTypes reviewer;
    @Schema(description = "rating")
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double rating;
    @Schema(description = "review comment")
    @Size(max = 1000)
    private String comment;
    @Schema(description = "when when the review was left")
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @Schema(description = "when was entity last updated date")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
