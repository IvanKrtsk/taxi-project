package by.ikrotsyuk.bsuir.communicationparts.event;

import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes;

import java.math.BigDecimal;

public record IncomePaymentArtemisDTO(
        Long rideId,

        Long passengerId,

        Long driverId,

        BigDecimal amount,

        PaymentTypes paymentType
) {
}
