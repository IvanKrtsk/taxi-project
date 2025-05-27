package by.ikrotsyuk.bsuir.paymentservice.controller.promocode;

import by.ikrotsyuk.bsuir.paymentservice.dto.response.IncomePaymentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserPromoCodesOperations {
    @PatchMapping("/{userId}/payments/promo-codes/{code}")
    ResponseEntity<IncomePaymentResponseDTO> usePromoCode(@PathVariable Long userId, @PathVariable String code);
}
