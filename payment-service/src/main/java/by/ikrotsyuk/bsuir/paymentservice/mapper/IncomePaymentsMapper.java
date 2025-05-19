package by.ikrotsyuk.bsuir.paymentservice.mapper;

import by.ikrotsyuk.bsuir.communicationparts.event.FinishRideIncomePaymentDTO;
import by.ikrotsyuk.bsuir.communicationparts.event.IncomePaymentArtemisDTO;
import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.PaymentTypes;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentPassengerRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.request.IncomePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.IncomePaymentResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.IncomePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.IncomePaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IncomePaymentsMapper {
    IncomePaymentResponseDTO toResponseDTO(IncomePaymentEntity incomePaymentEntity);

    IncomePaymentFullResponseDTO toFullResponseDTO(IncomePaymentEntity incomePaymentEntity);

    List<IncomePaymentFullResponseDTO> toFullResponseDTOList(List<IncomePaymentEntity> incomePaymentEntity);

    IncomePaymentEntity toEntity(IncomePaymentRequestDTO incomePaymentRequestDTO);

    IncomePaymentEntity toPassengerIncomeEntity(IncomePaymentPassengerRequestDTO incomePaymentPassengerRequestDTO);

    IncomePaymentEntity toEntityArtemis(IncomePaymentArtemisDTO incomePaymentArtemisDTO);

    @Mapping(source = "paymentType", target = "paymentType")
    FinishRideIncomePaymentDTO toFinishRideIncomePaymentDTO(IncomePaymentEntity incomePaymentEntity, PaymentTypes paymentType);
}
