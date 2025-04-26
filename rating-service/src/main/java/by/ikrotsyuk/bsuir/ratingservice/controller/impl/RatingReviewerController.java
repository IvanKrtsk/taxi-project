package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingReviewerOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingReviewerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/reviewer/ratings/")
public class RatingReviewerController implements RatingReviewerOperations {
    private final RatingReviewerService ratingReviewerService;

    @Override
    public ResponseEntity<RatingResponseDTO> leaveReview(RatingRequestDTO ratingRequestDTO) {
        return new ResponseEntity<>(ratingReviewerService.leaveReview(ratingRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Page<RatingResponseDTO>> viewLeavedReviews(Long reviewerId, ReviewerTypes reviewerType, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(ratingReviewerService.viewLeavedReviews(reviewerId, reviewerType, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RatingResponseDTO> getReviewById(String ratingId) {
        return new ResponseEntity<>(ratingReviewerService.getReviewById(ratingId), HttpStatus.OK);
    }
}
