package by.ikrotsyuk.bsuir.paymentservice.mapper;

import by.ikrotsyuk.bsuir.paymentservice.dto.response.ExpensePaymentResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.ExpensePaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpensePaymentsMapper {
    ExpensePaymentResponseDTO toDTO(ExpensePaymentEntity expensePaymentEntity);
}
