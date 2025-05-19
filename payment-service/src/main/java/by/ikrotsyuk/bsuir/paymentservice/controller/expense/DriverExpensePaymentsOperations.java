package by.ikrotsyuk.bsuir.paymentservice.controller.expense;

import by.ikrotsyuk.bsuir.paymentservice.dto.response.ExpensePaymentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public interface DriverExpensePaymentsOperations {
    @PostMapping("/{userId}/payments/expenses")
    ResponseEntity<ExpensePaymentResponseDTO> transferMoney(@PathVariable Long userId, @RequestParam BigDecimal amount);
}
