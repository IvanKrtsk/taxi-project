package by.ikrotsyuk.bsuir.paymentservice.controller.card;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.BankCardResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserBankCardsOperations {
    @PostMapping("/{userId}/payments/cards")
    ResponseEntity<BankCardFullResponseDTO> addCard(@PathVariable Long userId, @RequestParam AccountTypes accountType, @RequestBody BankCardRequestDTO bankCardRequestDTO);

    @GetMapping("/{userId}/payments/cards")
    ResponseEntity<List<BankCardFullResponseDTO>> getCards(@PathVariable Long userId, @RequestParam AccountTypes accountType);

    @PatchMapping("/{userId}/payments/cards/{cardId}")
    ResponseEntity<BankCardResponseDTO> chooseCard(@PathVariable Long userId, @PathVariable Long cardId, @RequestParam AccountTypes accountType);

    @DeleteMapping("/{userId}/payments/cards/{cardId}")
    ResponseEntity<BankCardResponseDTO> deleteCard(@PathVariable Long userId, @PathVariable Long cardId, @RequestParam AccountTypes accountType);
}
