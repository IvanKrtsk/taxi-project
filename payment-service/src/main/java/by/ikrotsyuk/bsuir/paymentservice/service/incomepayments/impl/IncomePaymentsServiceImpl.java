package by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.IncomePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts.AccountNotFoundByUserIdAndAccountTypeException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes.IncomeForRideAndAccountIdAlreadyExistsException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes.IncomeNotFoundByIdException;
import by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes.IncomesNotFoundException;
import by.ikrotsyuk.bsuir.paymentservice.mapper.IncomePaymentsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.AccountsRepository;
import by.ikrotsyuk.bsuir.paymentservice.repository.IncomePaymentsRepository;
import by.ikrotsyuk.bsuir.paymentservice.service.accounts.AccountsService;
import by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.IncomePaymentsService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomePaymentsServiceImpl implements IncomePaymentsService {
    private final IncomePaymentsRepository incomePaymentsRepository;
    private final IncomePaymentsMapper incomePaymentsMapper;
    private final AccountsRepository accountsRepository;
    private final AccountsService accountsService;

    @Override
    public IncomePaymentFullResponseDTO createIncomePayment(IncomePaymentRequestDTO incomePaymentRequestDTO) {
        if(incomePaymentsRepository.existsByAccountIdAndRideId(incomePaymentRequestDTO.accountId(), incomePaymentRequestDTO.rideId()))
            throw new IncomeForRideAndAccountIdAlreadyExistsException(incomePaymentRequestDTO.accountId(), incomePaymentRequestDTO.rideId());
        else {
            IncomePaymentEntity incomePaymentEntity = incomePaymentsMapper.toEntity(incomePaymentRequestDTO);
            AccountEntity driverAccount = accountsRepository.findFirstWithLockingByUserIdAndAccountType(incomePaymentEntity.getDriverId(), AccountTypes.DRIVER)
                            .orElseThrow(() -> new AccountNotFoundByUserIdAndAccountTypeException(incomePaymentEntity.getDriverId(), AccountTypes.DRIVER));
            accountsService.addAmount(incomePaymentEntity.getDriverId(), AccountTypes.DRIVER, incomePaymentEntity.getAmount());
            incomePaymentsRepository.save(incomePaymentEntity);
            return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
        }
    }

    @Override
    public List<IncomePaymentFullResponseDTO> getIncomePayments() {
        List<IncomePaymentEntity> incomePaymentEntityList = incomePaymentsRepository.findAll();
        if(incomePaymentEntityList.isEmpty())
            throw new IncomesNotFoundException();
        else
            return incomePaymentsMapper.toFullResponseDTOList(incomePaymentEntityList);
    }

    @Override
    public IncomePaymentFullResponseDTO getIncomePayment(ObjectId incomeId) {
        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findById(incomeId)
                .orElseThrow(() -> new IncomeNotFoundByIdException(incomeId));
        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }

    @Override
    public IncomePaymentFullResponseDTO updateIncomePayment(ObjectId incomeId, IncomePaymentRequestDTO incomePaymentRequestDTO) {
        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findById(incomeId)
                .orElseThrow(() -> new IncomeNotFoundByIdException(incomeId));

        incomePaymentEntity.setAccountId(incomePaymentRequestDTO.accountId());
        incomePaymentEntity.setRideId(incomePaymentRequestDTO.rideId());
        incomePaymentEntity.setAmount(incomePaymentRequestDTO.amount());
        incomePaymentEntity.setPaymentType(incomePaymentRequestDTO.paymentTypes());

        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }

    @Override
    public IncomePaymentFullResponseDTO deleteIncomePayment(ObjectId incomeId) {
        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findById(incomeId)
                .orElseThrow(() -> new IncomeNotFoundByIdException(incomeId));

        incomePaymentsRepository.deleteById(incomeId);

        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }
}
