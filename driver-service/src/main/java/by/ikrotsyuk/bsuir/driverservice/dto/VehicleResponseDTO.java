package by.ikrotsyuk.bsuir.driverservice.dto;

import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleResponseDTO {
    @Schema(description = "driver's vehicle id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "driver's car brand")
    @NotBlank
    @Size(max = 50)
    private String brand;
    @Schema(description = "driver's car model")
    @NotBlank
    @Size(max = 50)
    private String model;
    @Schema(description = "driver's car class")
    @Enumerated(EnumType.STRING)
    @NotNull
    private CarClassTypes carClassTypes;
    @Schema(description = "number of driver trips on this car")
    @NotNull
    private Long ridesCount;
    @Schema(description = "date of manufacture of the car")
    @NotNull
    private Integer year;
    @Schema(description = "car number plate")
    @Column(unique = true)
    @NotBlank
    @Size(max = 20)
    private String licensePlate;
    @Schema(description = "driver's car color")
    @NotBlank
    @Size(max = 30)
    private String color;
    @ManyToOne
    private DriverEntity driver;
    @Schema(description = "is car current")
    private Boolean isCurrent;
}
