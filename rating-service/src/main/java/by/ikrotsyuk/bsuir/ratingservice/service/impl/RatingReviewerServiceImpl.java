package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingReviewerService;
import org.springframework.data.domain.Page;

public class RatingReviewerServiceImpl implements RatingReviewerService {
    @Override
    public RatingResponseDTO leaveReview(RatingRequestDTO ratingRequestDTO) {
        return null;
    }

    @Override
    public Page<RatingResponseDTO> viewLeavedReviews(Long reviewerId, ReviewerTypesRating reviewerType) {
        return null;
    }
}
