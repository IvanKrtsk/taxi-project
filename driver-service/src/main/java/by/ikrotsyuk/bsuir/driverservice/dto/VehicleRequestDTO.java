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
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 50, message = "field.must.have.size.message")
    String brand,

    @Schema(description = "driver's car model")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 50, message = "field.must.have.size.message")
    String model,

    @Schema(description = "driver's car class")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "field.must.not.be.empty.message")
    CarClassTypes carClass,

    @Schema(description = "date of manufacture of the car")
    @Max(value = 2030, message = "field.must.have.size.message")
    @NotNull(message = "field.must.not.be.empty.message")
    Integer year,

    @Schema(description = "car number plate")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 20, message = "field.must.have.size.message")
    String licensePlate,

    @Schema(description = "driver's car color")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 30, message = "field.must.have.size.message")
    String color
){}
