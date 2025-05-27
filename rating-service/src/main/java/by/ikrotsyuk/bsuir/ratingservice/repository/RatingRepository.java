package by.ikrotsyuk.bsuir.ratingservice.repository;

import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<RatingEntity, ObjectId> {
    Page<RatingEntity> findAllByReviewerIdAndReviewerType(Long reviewerId, ReviewerTypes reviewerType, Pageable pageable);

    boolean existsByRideIdAndReviewerType(Long rideId, ReviewerTypes reviewerType);
}
