package by.ikrotsyuk.bsuir.paymentservice.entity;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "internal_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InternalAccountEntity {
    @Schema(description = "account id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "user id")
    @NotNull
    @Min(1)
    private Long userId;
    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountTypes accountTypes;
    @Schema(description = "internal balance")
    @NotNull
    private Double balance;
    @Schema(description = "is internal bank account deleted")
    private Boolean isDeleted;
    @Schema(description = "account creation date")
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @Schema(description = "when was entity last updated date")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
