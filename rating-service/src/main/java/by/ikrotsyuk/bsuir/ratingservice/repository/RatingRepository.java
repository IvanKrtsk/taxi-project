package by.ikrotsyuk.bsuir.ratingservice.repository;

import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<RatingEntity, ObjectId> {
    Page<RatingEntity> findAllByReviewerIdAndReviewerType(Long reviewerId, ReviewerTypesRating reviewerType, Pageable pageable);
    Optional<RatingEntity> findById(ObjectId reviewId);
}
