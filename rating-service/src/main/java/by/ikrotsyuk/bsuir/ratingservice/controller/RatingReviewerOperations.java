package by.ikrotsyuk.bsuir.ratingservice.controller;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RatingReviewerOperations {
    ResponseEntity<RatingResponseDTO> leaveReview(@Valid @RequestBody RatingRequestDTO ratingRequestDTO);
    ResponseEntity<Page<RatingResponseDTO>> viewLeavedReviews(@PathVariable Long reviewerId, @RequestParam ReviewerTypesRating reviewerType, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<RatingResponseDTO> getReviewById(@RequestParam String reviewId);
}
