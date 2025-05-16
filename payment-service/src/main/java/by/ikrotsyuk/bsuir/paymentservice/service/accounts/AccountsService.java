package by.ikrotsyuk.bsuir.paymentservice.service.accounts;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;

import java.util.List;

public interface AccountsService {
    AccountFullResponseDTO createAccount(AccountRequestDTO accountRequestDTO);
    List<AccountFullResponseDTO> getAccounts();
    AccountFullResponseDTO getAccount(Long accountId);
    AccountFullResponseDTO updateAccount(Long accountId, AccountRequestDTO accountRequestDTO);
    AccountFullResponseDTO updatePaymentMethod(Long accountId, PaymentTypes paymentType);
    AccountFullResponseDTO deleteAccount(Long accountId);
}
