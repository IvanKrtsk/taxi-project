package by.ikrotsyuk.bsuir.paymentservice.controller.income;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentPassengerRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface PassengerIncomePaymentsOperations {
    @PostMapping("/{userId}/payments/incomes")
    ResponseEntity<IncomePaymentFullResponseDTO> createIncomePayment(@PathVariable Long userId, @RequestBody IncomePaymentPassengerRequestDTO incomePaymentPassengerRequestDTO);

    @PatchMapping("/{userId}/payments/incomes")
    ResponseEntity<IncomePaymentFullResponseDTO> finishIncomePayment(@PathVariable Long userId, @RequestParam ObjectId incomeId);
}
