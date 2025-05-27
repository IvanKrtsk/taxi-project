package by.ikrotsyuk.bsuir.paymentservice.service.expensepayments;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.ExpensePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.ExpensePaymentFullResponseDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExpensePaymentsService {
    ExpensePaymentFullResponseDTO addExpensePayment(ExpensePaymentRequestDTO expensePaymentRequestDTO);
    List<ExpensePaymentFullResponseDTO> getExpensePayments();
    ExpensePaymentFullResponseDTO getExpensePayment(ObjectId paymentId);
    ExpensePaymentFullResponseDTO updateExpensePayment(ObjectId paymentId, ExpensePaymentRequestDTO expensePaymentRequestDTO);
    ExpensePaymentFullResponseDTO deleteExpensePayment(ObjectId paymentId);
}
