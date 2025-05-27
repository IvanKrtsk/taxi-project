package by.ikrotsyuk.bsuir.ratingservice.entity;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "unsent_reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UnsentRatingEntity {
    @Id
    private ObjectId id;
    private Long rideId;
    private Long reviewerId;
    private Long reviewedId;
    private ReviewerTypes reviewerType;
    private Double rating;
    private String exceptionMessage;
    @CreatedDate
    private Date createdAt;
}
