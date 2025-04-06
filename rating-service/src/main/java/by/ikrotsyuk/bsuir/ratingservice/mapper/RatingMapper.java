package by.ikrotsyuk.bsuir.ratingservice.mapper;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingEntity toEntity(RatingRequestDTO requestDTO);

    RatingResponseDTO toDTO(RatingEntity ratingEntity);
}
