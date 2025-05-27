package by.ikrotsyuk.bsuir.paymentservice.mapper;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.AccountRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.AccountFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountsMapper {
    AccountEntity toEntity(AccountRequestDTO accountRequestDTO);

    AccountFullResponseDTO toFullDTO(AccountEntity accountEntity);

    List<AccountFullResponseDTO> toFullDTOList(List<AccountEntity> accountEntityList);
}
