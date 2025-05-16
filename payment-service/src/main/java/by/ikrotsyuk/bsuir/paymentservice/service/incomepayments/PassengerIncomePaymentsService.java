package by.ikrotsyuk.bsuir.paymentservice.service.incomepayments;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentPassengerRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import org.bson.types.ObjectId;

public interface PassengerIncomePaymentsService {
    IncomePaymentFullResponseDTO createIncomePayment(Long userId, IncomePaymentPassengerRequestDTO incomePaymentPassengerRequestDTO);

    IncomePaymentFullResponseDTO finishIncomePayment(Long userId, ObjectId incomeId);

    void changePaymentType(Long accountId, PaymentTypes paymentType);
}
