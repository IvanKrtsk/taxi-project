package by.ikrotsyuk.bsuir.driverservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DriverRequestDTO (
    @Schema(description = "driver name")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 100, message = "field.must.have.size.message")
    String name,

    @Schema(description = "driver email")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Email
    @Size(max = 100, message = "field.must.have.size.message")
    String email,

    @Schema(description = "driver phone")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 15, message = "field.must.have.size.message")
    String phone
){}
