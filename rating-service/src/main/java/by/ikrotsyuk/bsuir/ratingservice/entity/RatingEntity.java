package by.ikrotsyuk.bsuir.ratingservice.entity;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
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
    @Id
    private ObjectId id;
    private Long rideId;
    private Long reviewerId;
    private Long reviewedId;
    private ReviewerTypes reviewerType;
    private Double rating;
    private String comment;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}
