package by.ikrotsyuk.bsuir.ratingservice.service.impl.validation;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotAcceptedException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToDriverException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToPassengerException;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingValidationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RatingValidationServiceImpl implements RatingValidationService {
    @Override
    public void checkIdMatch(RideFullResponseDTO rideFullResponseDTO, RatingRequestDTO ratingRequestDTO, ReviewerTypes reviewerType) {
        Long passengerId = (reviewerType == ReviewerTypes.PASSENGER) ? ratingRequestDTO.reviewerId() : ratingRequestDTO.reviewedId();
        Long driverId = (reviewerType == ReviewerTypes.DRIVER) ? ratingRequestDTO.reviewerId() : ratingRequestDTO.reviewedId();

        if(Objects.isNull(rideFullResponseDTO.driverId()))
            throw new RideNotAcceptedException(rideFullResponseDTO.id());
        if(!rideFullResponseDTO.passengerId().equals(passengerId))
            throw new RideNotBelongToPassengerException(rideFullResponseDTO.id(), passengerId);
        if(!rideFullResponseDTO.driverId().equals(driverId))
            throw new RideNotBelongToDriverException(rideFullResponseDTO.id(), driverId);
    }
}
