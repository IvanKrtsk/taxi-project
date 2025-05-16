package by.ikrotsyuk.bsuir.paymentservice.controller.card;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BankCardsOperations {
    @PostMapping("/{accountId}/payments/cards")
    ResponseEntity<BankCardFullResponseDTO> addBankCard(@PathVariable Long accountId, @RequestBody BankCardRequestDTO bankCardRequestDTO);

    @GetMapping("/payments/cards/{cardId}")
    ResponseEntity<BankCardFullResponseDTO> getBankCard(@PathVariable Long cardId);

    @GetMapping("/payments/cards")
    ResponseEntity<List<BankCardFullResponseDTO>> getBankCards();

    @PutMapping("/payments/cards/{cardId}")
    ResponseEntity<BankCardFullResponseDTO> updateBankCard(@PathVariable Long cardId, @RequestBody BankCardRequestDTO bankCardRequestDTO);

    @DeleteMapping("/payments/cards/{cardId}")
    ResponseEntity<BankCardFullResponseDTO> deleteBankCard(@PathVariable Long cardId);
}
