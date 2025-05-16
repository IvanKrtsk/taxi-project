package by.ikrotsyuk.bsuir.paymentservice.controller.income;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IncomePaymentsOperations {
    @PostMapping
    ResponseEntity<IncomePaymentFullResponseDTO> createIncomePayment(@RequestBody IncomePaymentRequestDTO incomePaymentRequestDTO);

    @GetMapping
    ResponseEntity<List<IncomePaymentFullResponseDTO>> getIncomePayments();

    @GetMapping("/{incomeId}")
    ResponseEntity<IncomePaymentFullResponseDTO> getIncomePayment(@PathVariable ObjectId incomeId);

    @PutMapping("/{incomeId}")
    ResponseEntity<IncomePaymentFullResponseDTO> updateIncomePayment(@PathVariable ObjectId incomeId, @RequestBody IncomePaymentRequestDTO incomePaymentRequestDTO);

    @DeleteMapping("/{incomeId}")
    ResponseEntity<IncomePaymentFullResponseDTO> deleteIncomePayment(@PathVariable ObjectId incomeId);
}
