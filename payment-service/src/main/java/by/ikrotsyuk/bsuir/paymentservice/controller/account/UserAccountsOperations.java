package by.ikrotsyuk.bsuir.paymentservice.controller.account;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserAccountsOperations {
    @GetMapping("/{userId}/payments/accounts")
    ResponseEntity<AccountFullResponseDTO> getAccount(@PathVariable Long userId, @RequestParam AccountTypes accountType);

    @PutMapping("/{userId}/payments/accounts")
    ResponseEntity<AccountFullResponseDTO> updateAccount(@PathVariable Long userId, @RequestParam AccountTypes accountType, @RequestBody AccountRequestDTO accountRequestDTO);

    @PatchMapping("/{userId}/payments/accounts")
    ResponseEntity<AccountFullResponseDTO> updatePaymentMethod(@PathVariable Long userId, @RequestParam AccountTypes accountType, @RequestParam PaymentTypes paymentType);

    @DeleteMapping("/{userId}/payments/accounts")
    ResponseEntity<AccountFullResponseDTO> deleteAccount(@PathVariable Long userId, @RequestParam AccountTypes accountType);
}
