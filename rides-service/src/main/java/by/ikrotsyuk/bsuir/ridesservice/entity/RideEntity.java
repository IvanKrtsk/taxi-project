package by.ikrotsyuk.bsuir.ridesservice.entity;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Schema(description = "ride id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "trip passenger id")
    @NotNull
    private Long passengerId;
    @Schema(description = "trip driver id")
    @NotNull
    private Long driverId;
    @Schema(description = "starting address")
    @NotBlank
    @Size(max = 255)
    private String startLocation;
    @Schema(description = "destination address")
    @NotBlank
    @Size(max = 255)
    private String endLocation;
    private BigDecimal cost;
    @Schema(description = "ride status")
    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private RideStatusTypes rideStatus;
    @Schema(description = "payment type")
    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private PaymentTypeTypes paymentType;
    @Schema(description = "rating by user")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double rating;
    @Schema(description = "taxi call time")
    @CreationTimestamp
    @NotNull
    private OffsetDateTime bookedAt;
    @Schema(description = "estimated waiting time")
    private Integer estimatedWaitingTime;
    @Schema(description = "taxi driver's confirmation time")
    private OffsetDateTime acceptedAt;
    @Schema(description = "trip start time")
    private OffsetDateTime beganAt;
    @Schema(description = "trip end time")
    private OffsetDateTime endedAt;
    @Schema(description = "when was entity last updated date")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
