package by.ikrotsyuk.bsuir.communicationparts.event;

import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentStatus;
import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record FinishRideIncomePaymentDTO(
        Long accountId,
        Long rideId,
        Long driverId,
        BigDecimal amount,
        PaymentTypes paymentType,
        PaymentStatus paymentStatus,
        OffsetDateTime createdAt,
        OffsetDateTime modifiedAt
) {
}
