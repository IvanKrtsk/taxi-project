package by.ikrotsyuk.bsuir.ratingservice.service.impl.validation.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.*;
import by.ikrotsyuk.bsuir.ratingservice.feign.RideClient;
import by.ikrotsyuk.bsuir.ratingservice.service.impl.validation.RatingValidationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RatingValidationServiceImpl implements RatingValidationService {
    private final RideClient rideClient;

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

    @Retry(name = "RIDES-SERVICE")
    @CircuitBreaker(name = "RIDES-SERVICE")
    @Override
    public RideFullResponseDTO getRideDTO(Long rideId) {
        RideFullResponseDTO rideFullResponseDTO;
        try {
            rideFullResponseDTO = rideClient.getRideById(rideId).getBody();
            return rideFullResponseDTO;
        }catch (feign.RetryableException e){
            throw new FeignConnectException();
        }
    }

}
