package by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentPassengerRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.IncomePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentStatus;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.mapper.IncomePaymentsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.IncomePaymentsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.cards.UserBankCardsService;
import by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.PassengerIncomePaymentsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PassengerIncomePaymentsServiceImpl implements PassengerIncomePaymentsService {
    private final AccountsRepository accountsRepository;
    private final IncomePaymentsRepository incomePaymentsRepository;
    private final IncomePaymentsMapper incomePaymentsMapper;
    private final UserBankCardsService userBankCardsService;

    @Override
    public IncomePaymentFullResponseDTO createIncomePayment(Long userId, IncomePaymentPassengerRequestDTO incomePaymentPassengerRequestDTO) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, AccountTypes.PASSENGER)
                .orElseThrow(() -> new RuntimeException("not found"));

        AccountEntity driverAccountEntity = accountsRepository.findByUserIdAndAccountType(incomePaymentPassengerRequestDTO.driverId(), AccountTypes.DRIVER)
                .orElseThrow(() -> new RuntimeException("not found"));

        IncomePaymentEntity incomePaymentEntity = incomePaymentsMapper.toPassengerIncomeEntity(incomePaymentPassengerRequestDTO);

        incomePaymentEntity.setAccountId(accountEntity.getId());
        incomePaymentEntity.setPaymentStatus(PaymentStatus.WAITING);
        incomePaymentsRepository.save(incomePaymentEntity);

        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }

    @Override
    public IncomePaymentFullResponseDTO finishIncomePayment(Long userId, ObjectId incomeId) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, AccountTypes.PASSENGER)
                .orElseThrow(() -> new RuntimeException("not found"));

        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findByAccountIdAndPaymentStatus(accountEntity.getId(), PaymentStatus.WAITING)
                .orElseThrow(() -> new RuntimeException("not found"));

        AccountEntity driverAccountEntity = accountsRepository.findByUserIdAndAccountType(incomePaymentEntity.getDriverId(), AccountTypes.DRIVER)
                .orElseThrow(() -> new RuntimeException("not found"));

        switch (incomePaymentEntity.getPaymentType()){
            case CARD -> {
                if(userBankCardsService.processPaymentWithCard(accountEntity.getId(), incomePaymentEntity.getAmount()))
                    incomePaymentEntity.setPaymentStatus(PaymentStatus.PAID);
                else{
                    incomePaymentEntity.setPaymentType(PaymentTypes.CASH);
                    incomePaymentEntity.setPaymentStatus(PaymentStatus.PAID);
                }
            }
            case CASH -> incomePaymentEntity.setPaymentStatus(PaymentStatus.PAID);
        }
        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }

    @Override
    public void changePaymentType(Long accountId, PaymentTypes paymentType) {
        if(accountsRepository.existsById(accountId)) {
            IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findByAccountIdAndPaymentStatus(accountId, PaymentStatus.WAITING)
                    .orElseThrow(() -> new RuntimeException("not found"));
            incomePaymentEntity.setPaymentType(paymentType);
        }else
            throw new RuntimeException("not found");
    }
}
