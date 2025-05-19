package by.ikrotsyuk.bsuir.paymentservice.controller.account;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AccountsOperations {
    @PostMapping
    ResponseEntity<AccountFullResponseDTO> createAccount(@RequestBody AccountRequestDTO accountRequestDTO);

    @GetMapping
    ResponseEntity<List<AccountFullResponseDTO>> getAccounts();

    @GetMapping("/{accountId}")
    ResponseEntity<AccountFullResponseDTO> getAccount(@PathVariable Long accountId);

    @PutMapping("/{accountId}")
    ResponseEntity<AccountFullResponseDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountRequestDTO accountRequestDTO);

    @PatchMapping("/{accountId}")
    ResponseEntity<AccountFullResponseDTO> updatePaymentMethod(@PathVariable Long accountId, @RequestParam PaymentTypes paymentType);

    @DeleteMapping("/{accountId}")
    ResponseEntity<AccountFullResponseDTO> deleteAccount(@PathVariable Long accountId);
}
