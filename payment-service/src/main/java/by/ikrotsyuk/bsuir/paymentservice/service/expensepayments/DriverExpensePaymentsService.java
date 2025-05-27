package by.ikrotsyuk.bsuir.paymentservice.service.expensepayments;

import by.ikrotsyuk.bsuir.paymentservice.dto.response.ExpensePaymentResponseDTO;

import java.math.BigDecimal;

public interface DriverExpensePaymentsService {
    ExpensePaymentResponseDTO transferMoney(Long userId, BigDecimal amount);
}
