package by.ikrotsyuk.bsuir.ratingservice.mapper;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingMapper {
    RatingEntity toEntity(RatingRequestDTO requestDTO);

    RatingResponseDTO toDTO(RatingEntity ratingEntity);

    RatingAdminResponseDTO toAdminDTO(RatingEntity ratingEntity);
}
