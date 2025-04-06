package by.ikrotsyuk.bsuir.ratingservice.service;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import org.springframework.data.domain.Page;

public interface RatingAdminService {
    RatingResponseDTO getRatingById(String id);
    Page<RatingResponseDTO> getAllRatings(int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    RatingResponseDTO editRating(String id, RatingRequestDTO requestDTO);
    RatingResponseDTO deleteRating(String id);
}
