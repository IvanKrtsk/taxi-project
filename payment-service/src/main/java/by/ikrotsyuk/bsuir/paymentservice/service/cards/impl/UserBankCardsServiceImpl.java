package by.ikrotsyuk.bsuir.paymentservice.service.cards.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.BankCardResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.BankCardEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.mapper.BankCardsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.BankCardsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.cards.UserBankCardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserBankCardsServiceImpl implements UserBankCardsService {
    private final BankCardsMapper bankCardsMapper;
    private final BankCardsRepository bankCardsRepository;
    private final AccountsRepository accountsRepository;

    @Override
    @Transactional
    public BankCardFullResponseDTO addCard(Long userId, AccountTypes accountType, BankCardRequestDTO bankCardRequestDTO) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new RuntimeException("not found"));
        BankCardEntity bankCardEntity = bankCardsMapper.toEntity(bankCardRequestDTO);
        bankCardEntity.setAccount(accountEntity);
        bankCardsRepository.save(bankCardEntity);
        return bankCardsMapper.toFullDTO(bankCardEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankCardFullResponseDTO> getCards(Long userId, AccountTypes accountType) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new RuntimeException("not found"));
        List<BankCardEntity> bankCardEntityList = bankCardsRepository.findAllByAccount(accountEntity);
        return bankCardsMapper.toFullDTOList(bankCardEntityList);
    }

    @Override
    @Transactional
    public BankCardResponseDTO chooseCard(Long userId, AccountTypes accountType, Long cardId) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new RuntimeException("not found"));

        List<BankCardEntity> bankCardEntityList = bankCardsRepository.findAllByAccount(accountEntity);
        if(bankCardEntityList.isEmpty())
            throw new RuntimeException("bank cards not assigned");

        BankCardEntity bankCardEntity = bankCardEntityList.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not found"));

        bankCardEntityList.forEach(card -> card.setIsChosen(false));

        bankCardEntity.setIsChosen(true);

        bankCardsRepository.saveAll(bankCardEntityList);
        return bankCardsMapper.toDTO(bankCardEntity);
    }

    @Override
    @Transactional
    public BankCardResponseDTO deleteCard(Long userId,  AccountTypes accountType, Long cardId) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new RuntimeException("not found"));
        List<BankCardEntity> bankCardEntityList = bankCardsRepository.findAllByAccount(accountEntity);
        if(bankCardEntityList.isEmpty())
            throw new RuntimeException("not found");
        else {
            BankCardEntity bankCardEntity = bankCardEntityList.stream()
                    .filter(card -> card.getId().equals(cardId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("not found"));
            if(bankCardEntity.getIsChosen() || bankCardEntityList.size() == 1)
                accountEntity.setSelectedPaymentType(PaymentTypes.CASH);
            bankCardsRepository.deleteById(cardId);
            return bankCardsMapper.toDTO(bankCardEntity);
        }
    }

    @Override
    @Transactional
    public boolean processPaymentWithCard(Long accountId, BigDecimal amount)     {
        AccountEntity accountEntity = accountsRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("not found"));

        List<BankCardEntity> bankCardEntityList = bankCardsRepository.findAllByAccount(accountEntity);
        if(bankCardEntityList.isEmpty())
            throw new RuntimeException("not found");
        else{
            BankCardEntity bankCardEntity = bankCardEntityList.stream()
                    .filter(BankCardEntity::getIsChosen)
                    .findFirst()
                    .orElse(bankCardEntityList.getFirst());

            int result =  processTransaction(amount);
            if(result == -1) {
                accountEntity.setSelectedPaymentType(PaymentTypes.CASH);
                return false;
            }else{
                return true;
            }
        }
    }

    public int processTransaction(BigDecimal amount){
        return amount.compareTo(BigDecimal.valueOf(new Random().nextLong(200)));
    }
}
