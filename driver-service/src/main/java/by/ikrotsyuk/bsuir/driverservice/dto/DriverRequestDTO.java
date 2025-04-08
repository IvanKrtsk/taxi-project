package by.ikrotsyuk.bsuir.driverservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DriverRequestDTO (
    @Schema(description = "driver name")
    @NotBlank
    @Size(max = 100)
    String name,

    @Schema(description = "driver email")
    @NotBlank
    @Email
    @Size(max = 100)
    String email,

    @Schema(description = "driver phone")
    @NotBlank
    @Size(max = 15)
    String phone
){}
