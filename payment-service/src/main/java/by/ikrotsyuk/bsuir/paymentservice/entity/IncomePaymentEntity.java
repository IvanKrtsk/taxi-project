package by.ikrotsyuk.bsuir.paymentservice.entity;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "income_payment_history")
public class IncomePaymentEntity {
    @Id
    private ObjectId id;
    private Long accountId;
    private Long rideId;
    private BigDecimal amount;
    private PaymentTypes paymentType;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime modifiedAt;
}
