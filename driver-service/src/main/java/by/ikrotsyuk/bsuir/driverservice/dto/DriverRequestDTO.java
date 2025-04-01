package by.ikrotsyuk.bsuir.driverservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
public class DriverRequestDTO {
    @Schema(description = "driver name")
    @NotBlank
    @Size(max = 100)
    private String name;
    @Schema(description = "driver email")
    @NotBlank
    @Column(unique = true)
    @Size(max = 100)
    private String email;
    @Schema(description = "driver phone")
    @NotBlank
    @Size(max = 15)
    private String phone;
}
