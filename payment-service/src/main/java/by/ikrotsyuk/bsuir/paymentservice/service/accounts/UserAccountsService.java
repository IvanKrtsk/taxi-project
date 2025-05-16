package by.ikrotsyuk.bsuir.paymentservice.service.accounts;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;

public interface UserAccountsService {
    AccountFullResponseDTO getAccount(Long userId, AccountTypes accountType);
    AccountFullResponseDTO updateAccount(Long userId, AccountTypes accountType, AccountRequestDTO accountRequestDTO);
    AccountFullResponseDTO updatePaymentMethod(Long userId, AccountTypes accountType, PaymentTypes paymentType);
    AccountFullResponseDTO deleteAccount(Long userId, AccountTypes accountType);
}
