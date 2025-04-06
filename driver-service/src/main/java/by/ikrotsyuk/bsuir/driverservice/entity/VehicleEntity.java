package by.ikrotsyuk.bsuir.driverservice.entity;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypesDriver;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.OffsetDateTime;

@Entity
@Table(name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private CarClassTypesDriver carClass;
    private Long ridesCount;
    private Integer year;
    @Column(unique = true)
    private String licensePlate;
    private String color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private DriverEntity driver;
    private Boolean isCurrent;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
