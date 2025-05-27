package by.ikrotsyuk.bsuir.paymentservice.service.incomepayments;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface IncomePaymentsService {
    IncomePaymentFullResponseDTO createIncomePayment(IncomePaymentRequestDTO incomePaymentRequestDTO);
    List<IncomePaymentFullResponseDTO> getIncomePayments();
    IncomePaymentFullResponseDTO getIncomePayment(ObjectId incomeId);
    IncomePaymentFullResponseDTO updateIncomePayment(ObjectId incomeId, IncomePaymentRequestDTO incomePaymentRequestDTO);
    IncomePaymentFullResponseDTO deleteIncomePayment(ObjectId incomeId);
}
