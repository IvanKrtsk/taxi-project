package by.ikrotsyuk.bsuir.passengerservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PassengerResponseDTO(
    @Schema(description = "passenger name")
    String name,

    @Schema(description = "passenger email")
    String email,

    @Schema(description = "passenger phone number")
    String phone,

    @Schema(description = "passenger rating")
    Double rating,

    @Schema(description = "passenger rides count")
    Long total_rides
){}
