package by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.ExpensePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.ExpensePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.BankCardEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.ExpensePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountBalanceIsLessThanAmountException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.cards.CardNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.expenses.ExpensePaymentNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.expenses.ExpensePaymentsNotFoundException;
import by.ikrotsyuk.bsuir.paymentservice.mapper.ExpensePaymentsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.BankCardsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.ExpensePaymentsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.accounts.AccountsService;
import by.ikrotsyuk.bsuir.paymentservice.service.expensepayments.ExpensePaymentsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpensePaymentsServiceImpl implements ExpensePaymentsService {
    private final ExpensePaymentsMapper expensePaymentsMapper;
    private final ExpensePaymentsRepository expensePaymentsRepository;
    private final AccountsRepository accountsRepository;
    private final AccountsService accountsService;
    private final BankCardsRepository bankCardsRepository;

    @Override
    public ExpensePaymentFullResponseDTO addExpensePayment(ExpensePaymentRequestDTO expensePaymentRequestDTO) {
        AccountEntity driverAccount = accountsRepository.findFirstWithLockingById(expensePaymentRequestDTO.accountId())
                .orElseThrow(() -> new AccountNotFoundByIdException(expensePaymentRequestDTO.accountId()));
        // transfer money to drivers selected card
        if(accountsService.subtractAmount(driverAccount.getUserId(), AccountTypes.DRIVER, expensePaymentRequestDTO.amount())){
            ExpensePaymentEntity expensePaymentEntity = expensePaymentsRepository.save(expensePaymentsMapper.toEntity(expensePaymentRequestDTO));
            return expensePaymentsMapper.toFullDTO(expensePaymentEntity);
        } else
            throw new AccountBalanceIsLessThanAmountException(expensePaymentRequestDTO.amount());
    }

    @Override
    public List<ExpensePaymentFullResponseDTO> getExpensePayments() {
        List<ExpensePaymentEntity> expensePaymentEntityList = expensePaymentsRepository.findAll();
        if(expensePaymentEntityList.isEmpty())
            throw new ExpensePaymentsNotFoundException();
        else
            return expensePaymentsMapper.toFullDTOList(expensePaymentEntityList);
    }

    @Override
    public ExpensePaymentFullResponseDTO getExpensePayment(ObjectId paymentId) {
        return expensePaymentsMapper.toFullDTO(expensePaymentsRepository.findById(paymentId)
                .orElseThrow(() -> new ExpensePaymentNotFoundByIdException(paymentId)));
    }

    @Override
    public ExpensePaymentFullResponseDTO updateExpensePayment(ObjectId paymentId, ExpensePaymentRequestDTO expensePaymentRequestDTO) {
        AccountEntity account = accountsRepository.findById(expensePaymentRequestDTO.accountId())
                .orElseThrow(() -> new AccountNotFoundByIdException(expensePaymentRequestDTO.accountId()));

        ExpensePaymentEntity expensePaymentEntity = expensePaymentsRepository.findById(paymentId)
                .orElseThrow(() -> new ExpensePaymentNotFoundByIdException(paymentId));

        BankCardEntity bankCardEntity = bankCardsRepository.findByAccountAndId(account, expensePaymentEntity.getCardId())
                .orElseThrow(() -> new CardNotFoundByIdException(expensePaymentRequestDTO.cardId()));

        if(accountsService.subtractAmount(account.getUserId(), AccountTypes.DRIVER, expensePaymentRequestDTO.amount())){
            // transfer money to a card
            expensePaymentEntity.setAmount(expensePaymentRequestDTO.amount());
            expensePaymentEntity.setCardId(expensePaymentEntity.getCardId());
            return expensePaymentsMapper.toFullDTO(expensePaymentEntity);
        }else
            throw new AccountBalanceIsLessThanAmountException(expensePaymentRequestDTO.amount());
    }

    @Override
    public ExpensePaymentFullResponseDTO deleteExpensePayment(ObjectId paymentId) {
        ExpensePaymentEntity expensePaymentEntity = expensePaymentsRepository.findById(paymentId)
                .orElseThrow(() -> new ExpensePaymentNotFoundByIdException(paymentId));

        expensePaymentsRepository.deleteById(paymentId);
        return expensePaymentsMapper.toFullDTO(expensePaymentEntity);
    }
}
