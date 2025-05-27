package by.ikrotsyuk.bsuir.ratingservice.service.impl.validation;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;

public interface RatingValidationService {
    void checkIdMatch(RideFullResponseDTO rideFullResponseDTO, RatingRequestDTO ratingRequestDTO, ReviewerTypes reviewerType);

    RideFullResponseDTO getRideDTO(Long rideId);
}
