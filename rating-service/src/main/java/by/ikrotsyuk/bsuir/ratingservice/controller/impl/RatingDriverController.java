package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingReviewerOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/rating/driver")
public class RatingDriverController implements RatingReviewerOperations {
    @Override
    public ResponseEntity<RatingResponseDTO> leaveReview(@Valid @RequestBody RatingRequestDTO ratingRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Page<RatingResponseDTO>> viewLeavedReviews(@PathVariable Long reviewerId, ReviewerTypesRating reviewerType) {
        return null;
    }
}
