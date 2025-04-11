package by.ikrotsyuk.bsuir.driverservice.dto;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import io.swagger.v3.oas.annotations.media.Schema;

public record VehicleResponseDTO(
    @Schema(description = "driver's vehicle id")
    Long id,

    @Schema(description = "driver's car brand")
    String brand,

    @Schema(description = "driver's car model")
    String model,

    @Schema(description = "driver's car class")
    CarClassTypes carClass,

    @Schema(description = "number of driver trips on this car")
    Long ridesCount,

    @Schema(description = "date of manufacture of the car")
    Integer year,

    @Schema(description = "car number plate")
    String licensePlate,

    @Schema(description = "driver's car color")
    String color,

    @Schema(description = "driver id")
    Long driverId,

    @Schema(description = "is car current")
    Boolean isCurrent
){}
