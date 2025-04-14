package by.ikrotsyuk.bsuir.ratingservice.entity;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypeTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private ReviewerTypeTypes reviewerType;
    private Double rating;
    private String comment;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
