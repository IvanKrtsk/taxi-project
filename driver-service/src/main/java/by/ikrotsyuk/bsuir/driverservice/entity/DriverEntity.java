package by.ikrotsyuk.bsuir.driverservice.entity;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.StatusTypesDriver;
import jakarta.persistence.*;
import lombok.*;
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
    private Double rating;
    private Long total_rides;
    private Boolean isDeleted;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private StatusTypesDriver status;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<VehicleEntity> driverVehicles;
}
