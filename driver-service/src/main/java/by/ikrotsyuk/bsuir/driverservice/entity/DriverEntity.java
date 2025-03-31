package by.ikrotsyuk.bsuir.driverservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DriverEntity {
    @Schema(description = "driver id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "driver name")
    @NotBlank
    @Size(max = 100)
    private String name;
    @Schema(description = "driver email")
    @NotBlank
    @Column(unique = true)
    @Size(max = 100)
    private String email;
    @Schema(description = "driver phone")
    @NotBlank
    @Size(max = 15)
    private String phone;
    @Schema(description = "driver rating")
    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double rating;
    @Schema(description = "number of driver trips")
    @NotNull
    private Long total_rides;
    @Schema(description = "is driver account deleted")
    @NotNull
    private Boolean isDeleted;
    @Schema(description = "entity creation time")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Schema(description = "when was entity last updated date")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<VehicleEntity> driverVehicles;
}
