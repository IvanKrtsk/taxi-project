package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingReviewerOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public class RatingDriverController implements RatingReviewerOperations {
    @Override
    public ResponseEntity<RatingResponseDTO> leaveReview(Long reviewerId, Long rideId) {
        return null;
    }

    @Override
    public ResponseEntity<Page<RatingResponseDTO>> viewLeavedReviews(Long reviewerId) {
        return null;
    }
}
