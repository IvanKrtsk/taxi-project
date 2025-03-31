package by.ikrotsyuk.bsuir.paymentservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.OffsetDateTime;

@Document(collection = "promo_codes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PromoCodeEntity {
    @Schema(description = "promo code id")
    @Id
    private ObjectId id;
    @Schema(description = "promo code text")
    @Indexed(unique = true)
    @NotNull
    private String code;
    @Schema(description = "percentage of discount")
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double discountPercentage;
    @Schema(description = "promo code start date")
    @NotNull
    private Instant startDate;
    @Schema(description = "promo code end date")
    @NotNull
    private Instant endDate;
    @Schema(description = "is promo code active")
    @NotNull
    private Boolean isActive;
    @Schema(description = "account creation date")
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @Schema(description = "when was entity last updated date")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
