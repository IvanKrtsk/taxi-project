package by.ikrotsyuk.bsuir.paymentservice.mapper;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.BankCardRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.BankCardResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.BankCardFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.BankCardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankCardsMapper {
    BankCardEntity toEntity(BankCardRequestDTO bankCardRequestDTO);
    BankCardFullResponseDTO toFullDTO(BankCardEntity bankCardEntity);
    List<BankCardFullResponseDTO> toFullDTOList(List<BankCardEntity> bankCardEntityList);
    BankCardResponseDTO toDTO(BankCardEntity bankCardEntity);
}
