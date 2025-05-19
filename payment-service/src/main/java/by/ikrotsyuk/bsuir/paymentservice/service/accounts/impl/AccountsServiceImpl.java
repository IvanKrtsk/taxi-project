package by.ikrotsyuk.bsuir.paymentservice.service.accounts.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountAlreadyExistsException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByUserIdAndAccountTypeException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountsNotFoundException;
import by.ikrotsyuk.bsuir.paymentservice.mapper.AccountsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.accounts.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountsServiceImpl implements AccountsService {
    private final AccountsMapper accountsMapper;
    private final AccountsRepository accountsRepository;

    @Override
    @Transactional
    public AccountFullResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        if(accountsRepository.existsByUserIdAndAccountType(accountRequestDTO.userId(), accountRequestDTO.accountType()))
            throw new AccountAlreadyExistsException(accountRequestDTO.userId(), accountRequestDTO.accountType());
        else {
            return accountsMapper
                    .toFullDTO(accountsRepository
                            .save(AccountEntity.builder()
                                .userId(accountRequestDTO.userId())
                                .accountType(accountRequestDTO.accountType())
                                .usedPromocodesCount(0L)
                                .build()
                            )
                    );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountFullResponseDTO> getAccounts() {
        List<AccountEntity> accountEntityList = accountsRepository.findAll();
        if(accountEntityList.isEmpty())
            throw new AccountsNotFoundException();
        else
            return accountsMapper.toFullDTOList(accountEntityList);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountFullResponseDTO getAccount(Long accountId) {
        AccountEntity accountEntity = accountsRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundByIdException(accountId));
        return accountsMapper.toFullDTO(accountEntity);
    }

    @Override
    @Transactional
    public AccountFullResponseDTO updateAccount(Long accountId, AccountRequestDTO accountRequestDTO) {
        AccountEntity accountEntity = accountsRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundByIdException(accountId));

        accountEntity.setUserId(accountRequestDTO.userId());
        accountEntity.setAccountType(accountRequestDTO.accountType());

        return accountsMapper.toFullDTO(accountEntity);
    }

    @Override
    @Transactional
    public AccountFullResponseDTO updatePaymentMethod(Long accountId, PaymentTypes paymentType) {
        AccountEntity accountEntity = accountsRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundByIdException(accountId));

        accountEntity.setSelectedPaymentType(paymentType);

        return accountsMapper.toFullDTO(accountEntity);
    }

    @Override
    @Transactional
    public AccountFullResponseDTO deleteAccount(Long accountId) {
        AccountEntity accountEntity = accountsRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundByIdException(accountId));

        accountsRepository.deleteById(accountId);

        return accountsMapper.toFullDTO(accountEntity);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean addAmount(Long userId, AccountTypes accountType, BigDecimal amount){
        AccountEntity account = accountsRepository.findFirstWithLockingByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new AccountNotFoundByUserIdAndAccountTypeException(userId, accountType));
        account.setBalance(account.getBalance().add(amount));
        return true;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean subtractAmount(Long userId, AccountTypes accountType, BigDecimal amount){
        AccountEntity account = accountsRepository.findFirstWithLockingByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new AccountNotFoundByUserIdAndAccountTypeException(userId, accountType));
        return switch (amount.compareTo(account.getBalance())) {
            case -1, 0 -> {
                account.setBalance(account.getBalance().subtract(amount));
                yield true;
            }
            case 1 -> false;
            default -> true;
        };
    }
}
