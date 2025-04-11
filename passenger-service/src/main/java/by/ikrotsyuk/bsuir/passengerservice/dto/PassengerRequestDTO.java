package by.ikrotsyuk.bsuir.passengerservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PassengerRequestDTO(
    @Schema(description = "passenger name")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 100, message = "field.must.have.size.message")
    String name,

    @Schema(description = "passenger email")
    @Column(unique = true)
    @NotBlank(message = "field.must.not.be.empty.message")
    @Email
    @Size(max = 100, message = "field.must.have.size.message")
    String email,

    @Schema(description = "passenger phone")
    @NotBlank(message = "field.must.not.be.empty.message")
    @Size(max = 15, message = "field.must.have.size.message")
    String phone
){}
