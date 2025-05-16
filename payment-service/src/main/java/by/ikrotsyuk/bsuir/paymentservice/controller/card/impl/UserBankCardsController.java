package by.ikrotsyuk.bsuir.paymentservice.controller.card.impl;

import by.ikrotsyuk.bsuir.paymentservice.controller.card.UserBankCardsOperations;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.BankCardResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.service.cards.UserBankCardsService;
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
@RequestMapping("/api/v1/users")
public class UserBankCardsController implements UserBankCardsOperations {
    private final UserBankCardsService userBankCardsService;

    @Override
    public ResponseEntity<BankCardFullResponseDTO> addCard(Long userId, AccountTypes accountType, BankCardRequestDTO bankCardRequestDTO) {
        return new ResponseEntity<>(userBankCardsService.addCard(userId, accountType, bankCardRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<BankCardFullResponseDTO>> getCards(Long userId, AccountTypes accountType) {
        return new ResponseEntity<>(userBankCardsService.getCards(userId, accountType), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankCardResponseDTO> chooseCard(Long userId, Long cardId, AccountTypes accountType) {
        return new ResponseEntity<>(userBankCardsService.chooseCard(userId, accountType, cardId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankCardResponseDTO> deleteCard(Long userId, Long cardId, AccountTypes accountType) {
        return new ResponseEntity<>(userBankCardsService.deleteCard(userId, accountType, cardId), HttpStatus.NO_CONTENT);
    }
}
