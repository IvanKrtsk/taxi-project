package by.ikrotsyuk.bsuir.paymentservice.controller.income.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.income.IncomePaymentsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.IncomePaymentsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments/incomes")
public class IncomePaymentsController implements IncomePaymentsOperations {
    private final IncomePaymentsService incomePaymentsService;

    @Override
    public ResponseEntity<IncomePaymentFullResponseDTO> createIncomePayment(IncomePaymentRequestDTO incomePaymentRequestDTO) {
        return new ResponseEntity<>(incomePaymentsService.createIncomePayment(incomePaymentRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<IncomePaymentFullResponseDTO>> getIncomePayments() {
        return new ResponseEntity<>(incomePaymentsService.getIncomePayments(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IncomePaymentFullResponseDTO> getIncomePayment(ObjectId incomeId) {
        return new ResponseEntity<>(incomePaymentsService.getIncomePayment(incomeId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IncomePaymentFullResponseDTO> updateIncomePayment(ObjectId incomeId, IncomePaymentRequestDTO incomePaymentRequestDTO) {
        return new ResponseEntity<>(incomePaymentsService.updateIncomePayment(incomeId, incomePaymentRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IncomePaymentFullResponseDTO> deleteIncomePayment(ObjectId incomeId) {
        return new ResponseEntity<>(incomePaymentsService.deleteIncomePayment(incomeId), HttpStatus.NO_CONTENT);
    }
}
