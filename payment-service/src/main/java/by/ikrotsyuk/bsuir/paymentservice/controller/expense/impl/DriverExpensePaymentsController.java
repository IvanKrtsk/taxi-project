package by.ikrotsyuk.bsuir.paymentservice.controller.expense.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.expense.DriverExpensePaymentsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.ExpensePaymentResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.DriverExpensePaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/users")
public class DriverExpensePaymentsController implements DriverExpensePaymentsOperations {
    private final DriverExpensePaymentsService driverExpensePaymentsService;

    @Override
    public ResponseEntity<ExpensePaymentResponseDTO> transferMoney(Long userId, BigDecimal amount) {
        return new ResponseEntity<>(driverExpensePaymentsService.transferMoney(userId, amount), HttpStatus.CREATED);
    }
}
