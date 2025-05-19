package by.ikrotsyuk.bsuir.paymentservice.service.cards.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.BankCardEntity;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.cards.CardAlreadyExistsException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.cards.CardNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.cards.CardsNotFoundException;
import by.ikrotsyuk.bsuir.paymentservice.mapper.BankCardsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.BankCardsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.cards.BankCardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BankCardsServiceImpl implements BankCardsService {
    private final BankCardsMapper bankCardsMapper;
    private final BankCardsRepository bankCardsRepository;
    private final AccountsRepository accountsRepository;

    @Override
    @Transactional
    public BankCardFullResponseDTO addBankCard(Long accountId, BankCardRequestDTO bankCardRequestDTO) {
        AccountEntity accountEntity = accountsRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundByIdException(accountId));
        if(bankCardsRepository.existsByCardNumberAndExpirationDateAndAccount(bankCardRequestDTO.cardNumber(), bankCardRequestDTO.expirationDate(), accountEntity))
            throw new CardAlreadyExistsException(bankCardRequestDTO.cardNumber());
        BankCardEntity bankCardEntity = bankCardsMapper.toEntity(bankCardRequestDTO);
        bankCardEntity.setAccount(accountEntity);
        bankCardsRepository.save(bankCardEntity);
        return bankCardsMapper.toFullDTO(bankCardEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public BankCardFullResponseDTO getBankCard(Long cardId) {
        BankCardEntity bankCardEntity = bankCardsRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundByIdException(cardId));
        return bankCardsMapper.toFullDTO(bankCardEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankCardFullResponseDTO> getBankCards() {
        List<BankCardEntity> bankCardEntityList = bankCardsRepository.findAll();
        if(bankCardEntityList.isEmpty())
            throw new CardsNotFoundException();
        return bankCardsMapper.toFullDTOList(bankCardEntityList);
    }

    @Override
    @Transactional
    public BankCardFullResponseDTO updateBankCard(Long cardId, BankCardRequestDTO bankCardRequestDTO) {
        BankCardEntity bankCardEntity = bankCardsRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundByIdException(cardId));
        bankCardEntity.setCardNumber(bankCardRequestDTO.cardNumber());
        bankCardEntity.setExpirationDate(bankCardRequestDTO.expirationDate());
        bankCardEntity.setIsChosen(bankCardRequestDTO.isChosen());
        return bankCardsMapper.toFullDTO(bankCardEntity);
    }

    @Override
    @Transactional
    public BankCardFullResponseDTO deleteBankCard(Long cardId) {
        BankCardEntity bankCardEntity = bankCardsRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundByIdException(cardId));
        bankCardsRepository.deleteById(cardId);
        return bankCardsMapper.toFullDTO(bankCardEntity);
    }
}
