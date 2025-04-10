package by.ikrotsyuk.bsuir.ratingservice.service;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import org.springframework.data.domain.Page;

public interface RatingAdminService {
    RatingAdminResponseDTO getReviewById(String reviewId);
    Page<RatingAdminResponseDTO> getAllReviews(int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    RatingAdminResponseDTO editReview(String reviewId, RatingRequestDTO requestDTO);
    RatingAdminResponseDTO deleteReview(String reviewId);
}
