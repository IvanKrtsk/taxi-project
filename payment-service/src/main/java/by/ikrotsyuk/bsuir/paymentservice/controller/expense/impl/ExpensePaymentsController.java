package by.ikrotsyuk.bsuir.paymentservice.controller.expense.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.expense.ExpensePaymentsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.ExpensePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.ExpensePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.ExpensePaymentsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/payments/expenses")
public class ExpensePaymentsController implements ExpensePaymentsOperations {
    private final ExpensePaymentsService expensePaymentsService;

    @Override
    public ResponseEntity<ExpensePaymentFullResponseDTO> addExpensePayment(ExpensePaymentRequestDTO expensePaymentRequestDTO) {
        return new ResponseEntity<>(expensePaymentsService.addExpensePayment(expensePaymentRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ExpensePaymentFullResponseDTO>> getExpensePayments() {
        return new ResponseEntity<>(expensePaymentsService.getExpensePayments(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ExpensePaymentFullResponseDTO> getExpensePayment(ObjectId paymentId) {
        return new ResponseEntity<>(expensePaymentsService.getExpensePayment(paymentId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ExpensePaymentFullResponseDTO> updateExpensePayment(ObjectId paymentId, ExpensePaymentRequestDTO expensePaymentRequestDTO) {
        return new ResponseEntity<>(expensePaymentsService.updateExpensePayment(paymentId, expensePaymentRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ExpensePaymentFullResponseDTO> deleteExpensePayment(ObjectId paymentId) {
        return new ResponseEntity<>(expensePaymentsService.deleteExpensePayment(paymentId), HttpStatus.NOT_FOUND);
    }
}
