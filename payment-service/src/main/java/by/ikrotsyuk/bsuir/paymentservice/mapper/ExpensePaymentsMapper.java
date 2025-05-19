package by.ikrotsyuk.bsuir.paymentservice.mapper;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.ExpensePaymentRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.ExpensePaymentResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.ExpensePaymentFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.ExpensePaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpensePaymentsMapper {
    ExpensePaymentResponseDTO toDTO(ExpensePaymentEntity expensePaymentEntity);

    ExpensePaymentFullResponseDTO toFullDTO(ExpensePaymentEntity expensePaymentEntity);

    ExpensePaymentEntity toEntity(ExpensePaymentRequestDTO expensePaymentRequestDTO);

    List<ExpensePaymentFullResponseDTO> toFullDTOList(List<ExpensePaymentEntity> expensePaymentEntityList);
}
