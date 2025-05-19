package by.ikrotsyuk.bsuir.paymentservice.service.promocodes.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.response.IncomePaymentResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.IncomePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.PromoCodeEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentStatus;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByUserIdAndAccountTypeException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes.IncomeNotFoundByAccountIdAndPaymentStatusException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.promocodes.PromoCodeNotFoundByCodeException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.promocodes.PromoCodeWithCodeDisabledException;
import by.ikrotsyuk.bsuir.paymentservice.mapper.IncomePaymentsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.IncomePaymentsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.PromoCodesRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.promocodes.UserPromoCodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class UserPromoCodesServiceImpl implements UserPromoCodesService {
    private final PromoCodesRepository promoCodesRepository;
    private final IncomePaymentsRepository incomePaymentsRepository;
    private final AccountsRepository accountsRepository;
    private final IncomePaymentsMapper incomePaymentsMapper;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public IncomePaymentResponseDTO usePromoCode(Long userId, String code) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, AccountTypes.PASSENGER)
                .orElseThrow(() -> new AccountNotFoundByUserIdAndAccountTypeException(userId, AccountTypes.PASSENGER));

        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findByAccountIdAndPaymentStatus(accountEntity.getId(), PaymentStatus.WAITING)
                .orElseThrow(() -> new IncomeNotFoundByAccountIdAndPaymentStatusException(accountEntity.getId(), PaymentStatus.WAITING));

        PromoCodeEntity promoCodeEntity = promoCodesRepository.findWithLockingByCode(code)
                .orElseThrow(() -> new PromoCodeNotFoundByCodeException(code));

        if(promoCodeEntity.getActivationsCount() > 0 && promoCodeEntity.getIsActive()) {
            accountEntity.setUsedPromocodesCount(accountEntity.getUsedPromocodesCount() + 1);
            promoCodeEntity.setActivationsCount(promoCodeEntity.getActivationsCount() - 1);
            BigDecimal amount = incomePaymentEntity.getAmount();
            incomePaymentEntity.setAmount(amount.multiply(BigDecimal.valueOf((100.0 - promoCodeEntity.getDiscountPercentage()) / 100.0)));
            if(promoCodeEntity.getActivationsCount().equals(0L))
                promoCodeEntity.setIsActive(false);
            return incomePaymentsMapper.toResponseDTO(incomePaymentEntity);
        } else
            throw new PromoCodeWithCodeDisabledException(code);
    }
}
