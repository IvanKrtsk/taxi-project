package by.ikrotsyuk.bsuir.ratingservice.entity;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypeTypes;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
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
    private ReviewerTypeTypes reviewerType;
    private Double rating;
    private String exceptionMessage;
    @CreationTimestamp
    private Date createdAt;
}
