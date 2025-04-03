package by.ikrotsyuk.bsuir.passengerservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PassengerRequestDTO(
    @Schema(description = "passenger name")
    @NotBlank
    @Size(max = 100)
    String name,

    @Schema(description = "passenger email")
    @Column(unique = true)
    @NotBlank
    @Email
    @Size(max = 100)
    String email,

    @Schema(description = "passenger phone")
    @NotBlank
    @Size(max = 15)
    String phone
){}
