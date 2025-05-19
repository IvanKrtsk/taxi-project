package by.ikrotsyuk.bsuir.paymentservice.controller.card.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.card.BankCardsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.service.cards.BankCardsService;
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
public class BankCardsController implements BankCardsOperations {
    private final BankCardsService bankCardsService;

    @Override
    public ResponseEntity<BankCardFullResponseDTO> addBankCard(Long accountId, BankCardRequestDTO bankCardRequestDTO) {
        return new ResponseEntity<>(bankCardsService.addBankCard(accountId, bankCardRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BankCardFullResponseDTO> getBankCard(Long cardId) {
        return new ResponseEntity<>(bankCardsService.getBankCard(cardId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankCardFullResponseDTO>> getBankCards() {
        return new ResponseEntity<>(bankCardsService.getBankCards(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankCardFullResponseDTO> updateBankCard(Long cardId, BankCardRequestDTO bankCardRequestDTO) {
        return new ResponseEntity<>(bankCardsService.updateBankCard(cardId, bankCardRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankCardFullResponseDTO> deleteBankCard(Long cardId) {
        return new ResponseEntity<>(bankCardsService.deleteBankCard(cardId), HttpStatus.NO_CONTENT);
    }
}
