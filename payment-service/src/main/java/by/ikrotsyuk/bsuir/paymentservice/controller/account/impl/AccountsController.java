package by.ikrotsyuk.bsuir.paymentservice.controller.account.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.account.AccountsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.service.accounts.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/payments/accounts")
public class AccountsController implements AccountsOperations {
    private final AccountsService accountsService;

    @Override
    public ResponseEntity<AccountFullResponseDTO> createAccount(AccountRequestDTO accountRequestDTO) {
        return new ResponseEntity<>(accountsService.createAccount(accountRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AccountFullResponseDTO>> getAccounts() {
        return new ResponseEntity<>(accountsService.getAccounts(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountFullResponseDTO> getAccount(Long accountId) {
        return new ResponseEntity<>(accountsService.getAccount(accountId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountFullResponseDTO> updateAccount(Long accountId, AccountRequestDTO accountRequestDTO) {
        return new ResponseEntity<>(accountsService.updateAccount(accountId, accountRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountFullResponseDTO> updatePaymentMethod(Long accountId, PaymentTypes paymentType) {
        return new ResponseEntity<>(accountsService.updatePaymentMethod(accountId, paymentType), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountFullResponseDTO> deleteAccount(Long accountId) {
        return new ResponseEntity<>(accountsService.deleteAccount(accountId), HttpStatus.NO_CONTENT);
    }
}
