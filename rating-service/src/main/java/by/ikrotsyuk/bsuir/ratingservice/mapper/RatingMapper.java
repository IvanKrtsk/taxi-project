package by.ikrotsyuk.bsuir.ratingservice.mapper;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "driverId", source = "reviewerId")
    RatingEntity toEntityDriverReview(RatingRequestDTO requestDTO);

    @Mapping(target = "reviewerId", source = "driverId")
    RatingEntity toEntityPassengerReview(RatingRequestDTO requestDTO);
}
