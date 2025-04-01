package by.ikrotsyuk.bsuir.passengerservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;


@Entity
@Table(name = "passengers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PassengerEntity {
    @Schema(description = "passenger id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "passenger name")
    @NotBlank
    @Size(max = 100)
    private String name;
    @Schema(description = "passenger email")
    @Column(unique = true)
    @NotBlank
    @Size(max = 100)
    private String email;
    @Schema(description = "passenger phone")
    @Column(unique = true)
    @NotBlank
    @Size(max = 15)
    private String phone;
    @Schema(description = "passenger rating")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double rating;
    @Schema(description = "passenger rides count")
    private Long totalRides;
    @Schema(description = "is passenger account deleted")
    private Boolean isDeleted;
    @Schema(description = "entity creation time")
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @Schema(description = "when was entity last updated date")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
