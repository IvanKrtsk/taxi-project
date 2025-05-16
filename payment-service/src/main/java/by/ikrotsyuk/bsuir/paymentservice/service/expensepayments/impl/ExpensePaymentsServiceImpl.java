package by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.ExpensePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.ExpensePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.ExpensePaymentsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpensePaymentsServiceImpl implements ExpensePaymentsService {
    @Override
    public ExpensePaymentFullResponseDTO addExpensePayment(ExpensePaymentRequestDTO expensePaymentRequestDTO) {
        return null;
    }

    @Override
    public List<ExpensePaymentFullResponseDTO> getExpensePayments() {
        return List.of();
    }

    @Override
    public ExpensePaymentFullResponseDTO getExpensePayment(ObjectId paymentId) {
        return null;
    }

    @Override
    public ExpensePaymentFullResponseDTO updateExpensePayment(ObjectId paymentId, ExpensePaymentRequestDTO expensePaymentRequestDTO) {
        return null;
    }

    @Override
    public ExpensePaymentFullResponseDTO deleteExpensePayment(ObjectId paymentId) {
        return null;
    }
}
