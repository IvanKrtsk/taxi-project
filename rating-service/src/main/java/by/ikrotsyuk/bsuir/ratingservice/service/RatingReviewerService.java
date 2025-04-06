package by.ikrotsyuk.bsuir.ratingservice.service;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import org.springframework.data.domain.Page;

public interface RatingReviewerService {
    RatingResponseDTO leaveReview(RatingRequestDTO ratingRequestDTO);
    Page<RatingResponseDTO> viewLeavedReviews(Long reviewerId, ReviewerTypesRating reviewerType, int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    RatingResponseDTO getReviewById(Long reviewId);
}
