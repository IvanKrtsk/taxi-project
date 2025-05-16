package by.ikrotsyuk.bsuir.paymentservice.service.cards;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.BankCardResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;

import java.math.BigDecimal;
import java.util.List;

public interface UserBankCardsService {
    BankCardFullResponseDTO addCard(Long userId, AccountTypes accountType, BankCardRequestDTO bankCardRequestDTO);
    List<BankCardFullResponseDTO> getCards(Long userId, AccountTypes accountType);
    BankCardResponseDTO chooseCard(Long userId, AccountTypes accountType, Long cardId);
    BankCardResponseDTO deleteCard(Long userId, AccountTypes accountType, Long cardId);
    boolean processPaymentWithCard(Long accountId, BigDecimal amount);
}
