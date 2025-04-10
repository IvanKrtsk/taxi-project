package by.ikrotsyuk.bsuir.passengerservice.entity;

import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypesPassenger;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.StatusTypesPassenger;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.OffsetDateTime;


@Entity
@Table(name = "passengers")
@FieldNameConstants(asEnum = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PassengerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private Double rating = 0.0;
    private Long totalRides = 0L;
    private Boolean isDeleted = false;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private StatusTypesPassenger status = StatusTypesPassenger.AVAILABLE;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private PaymentTypeTypesPassenger paymentType = PaymentTypeTypesPassenger.CASH;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
