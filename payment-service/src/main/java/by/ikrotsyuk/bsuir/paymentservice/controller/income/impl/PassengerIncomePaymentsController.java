package by.ikrotsyuk.bsuir.paymentservice.controller.income.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.income.PassengerIncomePaymentsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentPassengerRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.PassengerIncomePaymentsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
    public ResponseEntity<IncomePaymentFullResponseDTO> createIncomePayment(Long userId, IncomePaymentPassengerRequestDTO incomePaymentPassengerRequestDTO) {
        return new ResponseEntity<>(passengerIncomePaymentsService.createIncomePayment(userId, incomePaymentPassengerRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<IncomePaymentFullResponseDTO> finishIncomePayment(Long userId, ObjectId incomeId) {
        return new ResponseEntity<>(passengerIncomePaymentsService.finishIncomePayment(userId, incomeId), HttpStatus.OK);
    }
}
