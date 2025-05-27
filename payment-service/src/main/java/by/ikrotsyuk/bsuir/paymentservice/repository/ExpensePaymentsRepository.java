package by.ikrotsyuk.bsuir.paymentservice.repository;

import by.ikrotsyuk.bsuir.paymentservice.entity.ExpensePaymentEntity;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpensePaymentsRepository extends MongoRepository<ExpensePaymentEntity, ObjectId> {
}
