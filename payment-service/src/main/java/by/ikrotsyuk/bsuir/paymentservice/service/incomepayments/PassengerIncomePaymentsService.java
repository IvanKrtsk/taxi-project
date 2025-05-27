package by.ikrotsyuk.bsuir.paymentservice.service.incomepayments;

import by.ikrotsyuk.bsuir.communicationparts.event.FinishRideIncomePaymentDTO;
import by.ikrotsyuk.bsuir.communicationparts.event.IncomePaymentArtemisDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;

public interface PassengerIncomePaymentsService {
    void createIncomePayment(IncomePaymentArtemisDTO incomePaymentArtemisDTO);

    FinishRideIncomePaymentDTO finishIncomePayment(Long userId);

    void changePaymentType(Long accountId, PaymentTypes paymentType);
}
