package by.ikrotsyuk.bsuir.ratingservice.controller;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RatingReviewerOperations {
    ResponseEntity<RatingResponseDTO> leaveReview(@PathVariable Long reviewerId, @RequestParam Long rideId);
    ResponseEntity<Page<RatingResponseDTO>> viewLeavedReviews(@PathVariable Long reviewerId);
}
