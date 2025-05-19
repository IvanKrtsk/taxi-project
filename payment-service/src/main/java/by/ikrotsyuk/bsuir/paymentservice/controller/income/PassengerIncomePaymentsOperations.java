package by.ikrotsyuk.bsuir.paymentservice.controller.income;

import by.ikrotsyuk.bsuir.communicationparts.event.FinishRideIncomePaymentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface PassengerIncomePaymentsOperations {
    @PutMapping("/{userId}/payments/incomes")
    ResponseEntity<FinishRideIncomePaymentDTO> finishIncomePayment(@PathVariable Long userId);
}
