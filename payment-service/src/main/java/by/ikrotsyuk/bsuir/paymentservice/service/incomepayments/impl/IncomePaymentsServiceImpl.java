package by.ikrotsyuk.bsuir.paymentservice.service.incomepayments.impl;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.IncomePaymentEntity;
import by.ikrotsyuk.bsuir.paymentservice.mapper.IncomePaymentsMapper;
import by.ikrotsyuk.bsuir.paymentservice.repository.IncomePaymentsRepository;
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

    @Override
    public IncomePaymentFullResponseDTO createIncomePayment(IncomePaymentRequestDTO incomePaymentRequestDTO) {
        if(incomePaymentsRepository.existsByAccountIdAndRideId(incomePaymentRequestDTO.accountId(), incomePaymentRequestDTO.rideId()))
            throw new RuntimeException("already exists");
        else {
            IncomePaymentEntity incomePaymentEntity = incomePaymentsMapper.toEntity(incomePaymentRequestDTO);
            incomePaymentsRepository.save(incomePaymentEntity);
            incomePaymentsRepository.save(incomePaymentEntity);

            return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
        }
    }

    @Override
    public List<IncomePaymentFullResponseDTO> getIncomePayments() {
        List<IncomePaymentEntity> incomePaymentEntityList = incomePaymentsRepository.findAll();
        if(incomePaymentEntityList.isEmpty())
            throw new RuntimeException("not found");
        else
            return incomePaymentsMapper.toFullResponseDTOList(incomePaymentEntityList);
    }

    @Override
    public IncomePaymentFullResponseDTO getIncomePayment(ObjectId incomeId) {
        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("not found"));
        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }

    @Override
    public IncomePaymentFullResponseDTO updateIncomePayment(ObjectId incomeId, IncomePaymentRequestDTO incomePaymentRequestDTO) {
        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("not found"));

        incomePaymentEntity.setAccountId(incomePaymentRequestDTO.accountId());
        incomePaymentEntity.setRideId(incomePaymentRequestDTO.rideId());
        incomePaymentEntity.setAmount(incomePaymentRequestDTO.amount());
        incomePaymentEntity.setPaymentType(incomePaymentRequestDTO.paymentTypes());

        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }

    @Override
    public IncomePaymentFullResponseDTO deleteIncomePayment(ObjectId incomeId) {
        IncomePaymentEntity incomePaymentEntity = incomePaymentsRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("not found"));

        incomePaymentsRepository.deleteById(incomeId);

        return incomePaymentsMapper.toFullResponseDTO(incomePaymentEntity);
    }
}
