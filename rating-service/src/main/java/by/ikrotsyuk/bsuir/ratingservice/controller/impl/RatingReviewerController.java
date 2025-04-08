package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingReviewerOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingReviewerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/ratings/reviewer")
public class RatingReviewerController implements RatingReviewerOperations {
    private final RatingReviewerService ratingReviewerService;

    @Override
    @PostMapping
    public ResponseEntity<RatingResponseDTO> leaveReview(@Valid @RequestBody RatingRequestDTO ratingRequestDTO) {
        return new ResponseEntity<>(ratingReviewerService.leaveReview(ratingRequestDTO), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/all/{reviewerId}")
    public ResponseEntity<Page<RatingResponseDTO>> viewLeavedReviews(@PathVariable Long reviewerId, ReviewerTypesRating reviewerType, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(ratingReviewerService.viewLeavedReviews(reviewerId, reviewerType, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<RatingResponseDTO> getReviewById(String reviewId) {
        return new ResponseEntity<>(ratingReviewerService.getReviewById(reviewId), HttpStatus.OK);
    }
}
