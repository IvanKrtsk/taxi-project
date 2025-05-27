package by.ikrotsyuk.bsuir.paymentservice.controller.expense;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.ExpensePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.ExpensePaymentFullResponseDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ExpensePaymentsOperations {
    @PostMapping
    ResponseEntity<ExpensePaymentFullResponseDTO> addExpensePayment(@RequestBody ExpensePaymentRequestDTO expensePaymentRequestDTO);

    @GetMapping
    ResponseEntity<List<ExpensePaymentFullResponseDTO>> getExpensePayments();

    @GetMapping("/{paymentId}")
    ResponseEntity<ExpensePaymentFullResponseDTO> getExpensePayment(@PathVariable ObjectId paymentId);

    @PutMapping("/{paymentId}")
    ResponseEntity<ExpensePaymentFullResponseDTO> updateExpensePayment(@PathVariable ObjectId paymentId, @RequestBody ExpensePaymentRequestDTO expensePaymentRequestDTO);

    @DeleteMapping("/{paymentId}")
    ResponseEntity<ExpensePaymentFullResponseDTO> deleteExpensePayment(@PathVariable ObjectId paymentId);
}
