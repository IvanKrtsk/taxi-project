package by.ikrotsyuk.bsuir.paymentservice.mapper;

import by.ikrotsyuk.bsuir.paymentservice.dto.request.PromoCodeRequestDTO;
import by.ikrotsyuk.bsuir.paymentservice.dto.response.full.PromoCodeFullResponseDTO;
import by.ikrotsyuk.bsuir.paymentservice.entity.PromoCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromoCodesMapper {
    PromoCodeEntity toEntity(PromoCodeRequestDTO promoCodeRequestDTO);
    PromoCodeFullResponseDTO toFullDTO(PromoCodeEntity promoCodeEntity);
    List<PromoCodeFullResponseDTO> toFullDTOList(List<PromoCodeEntity> promoCodeEntityList);
}
