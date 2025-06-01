package by.ikrotsyuk.bsuir.passengerservice.dto;

import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypes;
import io.swagger.v3.oas.annotations.media.Schema;

public record PassengerResponseDTO(
    @Schema(description = "passenger id")
    Long id,

    @Schema(description = "passenger name")
    String name,

    @Schema(description = "passenger email")
    String email,

    @Schema(description = "passenger phone number")
    String phone,

    @Schema(description = "passenger rating")
    Double rating,

    @Schema(description = "passenger rides count")
    Long totalRides,

    @Schema(description = "is passenger deleted")
    Boolean isDeleted,

    @Schema(description = "passenger payment type")
    PaymentTypeTypes paymentType
){}
