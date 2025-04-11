package by.ikrotsyuk.bsuir.driverservice.dto;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VehicleRequestDTO(
    @Schema(description = "driver's car brand")
    @NotBlank
    @Size(max = 50)
    String brand,

    @Schema(description = "driver's car model")
    @NotBlank
    @Size(max = 50)
    String model,

    @Schema(description = "driver's car class")
    @Enumerated(EnumType.STRING)
    @NotNull
    CarClassTypes carClass,

    @Schema(description = "date of manufacture of the car")
    @Max(2030)
    @NotNull
    Integer year,

    @Schema(description = "car number plate")
    @NotBlank
    @Size(max = 20)
    String licensePlate,

    @Schema(description = "driver's car color")
    @NotBlank
    @Size(max = 30)
    String color
){}
