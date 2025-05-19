package by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.FinishRideIncomePaymentDTO;
import by.ikrotsyuk.bsuir.communicationparts.event.IncomePaymentArtemisDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.IncomePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentStatus;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByUserIdAndAccountTypeException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes.IncomeNotFoundByAccountIdAndPaymentStatusException;
import by.ikrotsyuk.bsuir.paymentservice.mapper.IncomePaymentsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.IncomePaymentsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.cards.UserBankCardsService;
import by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.PassengerIncomePaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PassengerIncomePaymentsServiceImpl implements PassengerIncomePaymentsService {
    private final AccountsRepository accountsRepository;
    private final IncomePaymentsRepository incomePaymentsRepository;
    private final IncomePaymentsMapper incomePaymentsMapper;
    private final UserBankCardsService userBankCardsService;
    private final String queueName = "incomePaymentsQueue";

    @Override
    @JmsListener(destination = queueName)
    public void createIncomePayment(IncomePaymentArtemisDTO incomePaymentArtemisDTO) {
        System.out.println(incomePaymentArtemisDTO);
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(incomePaymentArtemisDTO.passengerId(), AccountTypes.PASSENGER)
                .orElseThrow(() -> new AccountNotFoundByUserIdAndAccountTypeException(incomePaymentArtemisDTO.passengerId(), AccountTypes.PASSENGER));

        if(accountsRepository.existsByUserIdAndAccountType(incomePaymentArtemisDTO.driverId(), AccountTypes.DRIVER)) {
            IncomePaymentEntity incomePaymentEntity = incomePaymentsMapper.toEntityArtemis(incomePaymentArtemisDTO);

            incomePaymentEntity.setAccountId(accountEntity.getId());
            incomePaymentEntity.setPaymentStatus(PaymentStatus.WAITING);
            incomePaymentEntity.setRideId(incomePaymentArtemisDTO.rideId());
            incomePaymentEntity.setDriverId(incomePaymentArtemisDTO.driverId());
            incomePaymentEntity.setAmount(incomePaymentArtemisDTO.amount());
            PaymentTypes paymentType = (incomePaymentArtemisDTO.paymentType().name().equals(PaymentTypes.CASH.name())) ? PaymentTypes.CASH : PaymentTypes.CARD;
            incomePaymentEntity.setPaymentType(paymentType);
            incomePaymentsRepository.save(incomePaymentEntity);
        }
    }

    @Override
    public FinishRideIncomePaymentDTO finishIncomePayment(Long userId) {
        AccountEntity accountEntity = accountsRepository.findByUserIdAndAccountType(userId, AccountTypes.PASSENGER)
                .orElseThrow(() -> new AccountNotFoundByUserIdAndAccountTypeException(userId, AccountTypes.PASSENGER));

        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findByAccountIdAndPaymentStatus(accountEntity.getId(), PaymentStatus.WAITING)
                .orElseThrow(() -> new IncomeNotFoundByAccountIdAndPaymentStatusException(accountEntity.getId(), PaymentStatus.WAITING));

        if(accountsRepository.existsByUserIdAndAccountType(incomePaymentEntity.getDriverId(), AccountTypes.DRIVER)) {
            switch (incomePaymentEntity.getPaymentType()) {
                case CARD -> {
                    if (userBankCardsService.processPaymentWithCard(accountEntity.getId(), incomePaymentEntity.getDriverId(), incomePaymentEntity.getAmount()))
                        incomePaymentEntity.setPaymentStatus(PaymentStatus.PAID);
                    else {
                        incomePaymentEntity.setPaymentType(PaymentTypes.CASH);
                        incomePaymentEntity.setPaymentStatus(PaymentStatus.PAID);
                    }
                }
                case CASH -> incomePaymentEntity.setPaymentStatus(PaymentStatus.PAID);
            }
            System.err.println(incomePaymentEntity);
            return incomePaymentsMapper.toFinishRideIncomePaymentDTO(incomePaymentEntity, getPaymentType(incomePaymentEntity.getPaymentType()));
        } else
            throw new AccountNotFoundByUserIdAndAccountTypeException(incomePaymentEntity.getDriverId(), AccountTypes.DRIVER);
    }

    @Override
    public void changePaymentType(Long accountId, PaymentTypes paymentType) {
        if(accountsRepository.existsById(accountId)) {
            IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findByAccountIdAndPaymentStatus(accountId, PaymentStatus.WAITING)
                    .orElseThrow(() -> new IncomeNotFoundByAccountIdAndPaymentStatusException(accountId, PaymentStatus.WAITING));
            incomePaymentEntity.setPaymentType(paymentType);
        }else
            throw new AccountNotFoundByIdException(accountId);
    }

    private by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes getPaymentType(PaymentTypes paymentType){
        return paymentType.name().equals(by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes.CASH.name())
                ? by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes.CASH
                : by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes.CARD;
    }
}
