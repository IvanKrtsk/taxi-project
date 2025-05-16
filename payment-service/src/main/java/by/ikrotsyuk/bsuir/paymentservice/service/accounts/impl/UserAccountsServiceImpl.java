package by.ikrotsyuk.bsuir.paymentservice.service.accounts.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.mapper.AccountsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.accounts.UserAccountsService;
import by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.PassengerIncomePaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserAccountsServiceImpl implements UserAccountsService {
    private final AccountsMapper accountsMapper;
    private final AccountsRepository accountsRepository;
    private final PassengerIncomePaymentsService passengerIncomePaymentsService;

    @Override
    @Transactional(readOnly = true)
    public AccountFullResponseDTO getAccount(Long userId, AccountTypes accountType) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new RuntimeException("not found"));
        return accountsMapper.toFullDTO(accountEntity);
    }

    @Override
    @Transactional
    public AccountFullResponseDTO updateAccount(Long userId, AccountTypes accountType, AccountRequestDTO accountRequestDTO) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new RuntimeException("not found"));

        accountEntity.setUserId(accountRequestDTO.userId());
        accountEntity.setAccountType(accountRequestDTO.accountType());

        return accountsMapper.toFullDTO(accountEntity);
    }

    @Override
    @Transactional
    public AccountFullResponseDTO updatePaymentMethod(Long userId, AccountTypes accountType, PaymentTypes paymentType) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountType)
                .orElseThrow(() -> new RuntimeException("not found"));

        accountEntity.setSelectedPaymentType(paymentType);

        passengerIncomePaymentsService.changePaymentType(accountEntity.getId(), paymentType);

        return accountsMapper.toFullDTO(accountEntity);
    }

    @Override
    @Transactional
    public AccountFullResponseDTO deleteAccount(Long userId, AccountTypes accountTypes) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, accountTypes)
                .orElseThrow(() -> new RuntimeException("not found"));

        accountsRepository.deleteById(accountEntity.getId());

        return accountsMapper.toFullDTO(accountEntity);
    }
}
