package by.ikrotsyuk.bsuir.paymentservice.controller.income.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.FinishRideIncomePaymentDTO;
import by.ikrotsyuk.bsuir.paymentservice.controller.income.PassengerIncomePaymentsOperations;
import by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.PassengerIncomePaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class PassengerIncomePaymentsController implements PassengerIncomePaymentsOperations {
    private final PassengerIncomePaymentsService passengerIncomePaymentsService;

    @Override
    public ResponseEntity<FinishRideIncomePaymentDTO> finishIncomePayment(Long userId) {
        return new ResponseEntity<>(passengerIncomePaymentsService.finishIncomePayment(userId), HttpStatus.OK);
    }
}
