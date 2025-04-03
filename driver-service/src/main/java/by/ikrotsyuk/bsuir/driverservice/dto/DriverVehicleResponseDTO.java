package by.ikrotsyuk.bsuir.driverservice.dto;

import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverVehicleResponseDTO {
    @Schema(description = "driver id")
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
    private List<VehicleEntity> driverVehicles;
}
