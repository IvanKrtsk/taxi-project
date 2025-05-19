package by.ikrotsyuk.bsuir.paymentservice.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "expense_payment_history")
public class ExpensePaymentEntity {
    @Id
    private ObjectId id;
    private Long accountId;
    private Long cardId;
    private BigDecimal amount;
    @CreatedDate
    private OffsetDateTime createdAt;
    @LastModifiedDate
    private OffsetDateTime updatedAt;
}
