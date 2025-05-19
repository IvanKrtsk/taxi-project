package by.ikrotsyuk.bsuir.paymentservice.repository;

import by.ikrotsyuk.bsuir.paymentservice.entity.IncomePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IncomePaymentsRepository extends MongoRepository<IncomePaymentEntity, ObjectId> {
    Optional<IncomePaymentEntity> findByAccountIdAndPaymentStatus(Long accountId, PaymentStatus paymentStatus);
    boolean existsByAccountIdAndRideId(Long accountId, Long rideId);
}
