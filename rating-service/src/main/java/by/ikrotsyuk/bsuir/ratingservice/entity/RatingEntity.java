package by.ikrotsyuk.bsuir.ratingservice.entity;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;

@Document(collection = "reviews")
@FieldNameConstants(asEnum = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RatingEntity {
    @Schema(description = "review id")
    @Id
    private ObjectId id;
    private Long rideId;
    private Long reviewerId;
    private ReviewerTypesRating reviewerType;
    private Double rating;
    private String comment;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
