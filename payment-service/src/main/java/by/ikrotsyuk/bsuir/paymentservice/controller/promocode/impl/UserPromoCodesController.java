package by.ikrotsyuk.bsuir.paymentservice.controller.promocode.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.promocode.UserPromoCodesOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.IncomePaymentResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.promocodes.UserPromoCodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserPromoCodesController implements UserPromoCodesOperations {
    private final UserPromoCodesService userPromoCodesService;

    @Override
    public ResponseEntity<IncomePaymentResponseDTO> usePromoCode(Long userId, String code) {
        return new ResponseEntity<>(userPromoCodesService.usePromoCode(userId, code), HttpStatus.OK);
    }
}
