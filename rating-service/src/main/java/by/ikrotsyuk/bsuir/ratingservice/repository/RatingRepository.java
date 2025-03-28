package by.ikrotsyuk.bsuir.ratingservice.repository;

import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<RatingEntity, ObjectId> {
}
