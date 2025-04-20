package by.ikrotsyuk.bsuir.driverservice.entity;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.StatusTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "drivers")
@FieldNameConstants(asEnum = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @Builder.Default
    private Double rating = 0.0;
    @Builder.Default
    private Long totalRides = 0L;
    @Builder.Default
    private Boolean isDeleted = false;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private StatusTypes status;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<VehicleEntity> driverVehicles;
}
