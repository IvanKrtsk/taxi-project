package by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.response.ExpensePaymentResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.BankCardEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.ExpensePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.mapper.ExpensePaymentsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.BankCardsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.ExpensePaymentsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.DriverExpensePaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverExpensePaymentsServiceImpl implements DriverExpensePaymentsService {
    private final AccountsRepository accountsRepository;
    private final BankCardsRepository bankCardsRepository;
    private final ExpensePaymentsRepository expensePaymentsRepository;
    private final ExpensePaymentsMapper expensePaymentsMapper;

    @Override
    public ExpensePaymentResponseDTO transferMoney(Long userId, BigDecimal amount) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, AccountTypes.DRIVER)
                .orElseThrow(() -> new RuntimeException("not found"));

        List<BankCardEntity> bankCardEntityList = bankCardsRepository.findAllByAccount(accountEntity);
        if(bankCardEntityList.isEmpty())
            throw new RuntimeException("not found");
        else{
            BankCardEntity bankCardEntity = bankCardEntityList.stream()
                    .filter(BankCardEntity::getIsChosen)
                    .findFirst()
                    .orElse(bankCardEntityList.getFirst());

            ExpensePaymentEntity expensePaymentEntity = null;
            switch (amount.compareTo(accountEntity.getBalance())){
                case -1, 0:
                    // call money transfer to driver's bank card
                    expensePaymentEntity = ExpensePaymentEntity.builder()
                            .amount(amount)
                            .cardId(bankCardEntity.getId())
                            .accountId(accountEntity.getId())
                            .build();
                    accountEntity.setBalance(accountEntity.getBalance().subtract(amount));
                    break;
                case 1:
                    // call money transfer to driver's bank card
                    expensePaymentEntity = ExpensePaymentEntity.builder()
                            .amount(accountEntity.getBalance())
                            .cardId(bankCardEntity.getId())
                            .accountId(accountEntity.getId())
                            .build();
                    accountEntity.setBalance(BigDecimal.ZERO);
            }
            expensePaymentsRepository.save(expensePaymentEntity);
            return expensePaymentsMapper.toDTO(expensePaymentEntity);
        }
    }
}
