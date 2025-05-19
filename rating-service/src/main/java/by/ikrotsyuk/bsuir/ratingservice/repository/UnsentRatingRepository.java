package by.ikrotsyuk.bsuir.ratingservice.repository;

import by.ikrotsyuk.bsuir.ratingservice.entity.UnsentRatingEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UnsentRatingRepository extends MongoRepository<UnsentRatingEntity, ObjectId> {
}
