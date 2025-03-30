package by.ikrotsyuk.bsuir.passengerservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassengerResponseDTO {
    @Schema(description = "passenger name")
    @NotBlank
    @Size(max = 100)
    private String name;
    @Schema(description = "passenger email")
    @Column(unique = true)
    @NotBlank
    @Size(max = 100)
    private String email;
    @Schema(description = "passenger phone")
    @NotBlank
    @Size(max = 15)
    private String phone;
    @Schema(description = "passenger rating")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double rating;
    @Schema(description = "passenger rides count")
    private Long total_rides;
}
