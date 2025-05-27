package by.ikrotsyuk.bsuir.paymentservice.service.promocodes;

import by.ikrotsyuk.bsuir.paymentservice.dto.response.IncomePaymentResponseDTO;

public interface UserPromoCodesService {
    IncomePaymentResponseDTO usePromoCode(Long userId, String code);
}
