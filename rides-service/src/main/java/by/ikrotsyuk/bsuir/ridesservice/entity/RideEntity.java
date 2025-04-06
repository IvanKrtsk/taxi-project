package by.ikrotsyuk.bsuir.ridesservice.entity;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.CarClassTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypesRides;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypesRides;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "rides")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long passengerId;
    private Long driverId;
    private String startLocation;
    private String endLocation;
    private BigDecimal cost;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private RideStatusTypesRides rideStatus;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private PaymentTypeTypesRides paymentType;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private CarClassTypesRides carClass;
    @CreationTimestamp
    private OffsetDateTime bookedAt;
    private Integer estimatedWaitingTime;
    private OffsetDateTime acceptedAt;
    private OffsetDateTime beganAt;
    private OffsetDateTime endedAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}