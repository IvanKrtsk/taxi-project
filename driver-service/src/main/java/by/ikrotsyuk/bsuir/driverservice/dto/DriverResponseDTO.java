package by.ikrotsyuk.bsuir.driverservice.dto;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.StatusTypesDriver;
import io.swagger.v3.oas.annotations.media.Schema;

public record DriverResponseDTO (
    @Schema(description = "driver id")
    Long id,

    @Schema(description = "driver name")
    String name,

    @Schema(description = "driver email")
    String email,

    @Schema(description = "driver phone")
    String phone,

    @Schema(description = "driver rating")
    Double rating,

    @Schema(description = "number of driver trips")
    Long total_rides,

    @Schema(description = "is driver deleted")
    Boolean isDeleted,

    @Schema(description = "driver status")
    StatusTypesDriver status
){}
