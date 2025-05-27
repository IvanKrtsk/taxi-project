package by.ikrotsyuk.bsuir.paymentservice.service.cards;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;

import java.util.List;

public interface BankCardsService {
    BankCardFullResponseDTO addBankCard(Long accountId, BankCardRequestDTO bankCardRequestDTO);
    BankCardFullResponseDTO getBankCard(Long cardId);
    List<BankCardFullResponseDTO> getBankCards();
    BankCardFullResponseDTO updateBankCard(Long cardId, BankCardRequestDTO bankCardRequestDTO);
    BankCardFullResponseDTO deleteBankCard(Long cardId);
}
