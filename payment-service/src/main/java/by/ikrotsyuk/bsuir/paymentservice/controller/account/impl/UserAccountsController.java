package by.ikrotsyuk.bsuir.paymentservice.controller.account.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.account.UserAccountsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.service.accounts.UserAccountsService;
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
public class UserAccountsController implements UserAccountsOperations {
    private final UserAccountsService userAccountsService;

    @Override
    public ResponseEntity<AccountFullResponseDTO> getAccount(Long userId, AccountTypes accountType) {
        return new ResponseEntity<>(userAccountsService.getAccount(userId, accountType), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountFullResponseDTO> updateAccount(Long userId, AccountTypes accountType, AccountRequestDTO accountRequestDTO) {
        return new ResponseEntity<>(userAccountsService.updateAccount(userId, accountType, accountRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountFullResponseDTO> updatePaymentMethod(Long userId, AccountTypes accountType, PaymentTypes paymentType) {
        return new ResponseEntity<>(userAccountsService.updatePaymentMethod(userId, accountType, paymentType), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountFullResponseDTO> deleteAccount(Long userId, AccountTypes accountTypes) {
        return new ResponseEntity<>(userAccountsService.deleteAccount(userId, accountTypes), HttpStatus.NO_CONTENT);
    }
}
