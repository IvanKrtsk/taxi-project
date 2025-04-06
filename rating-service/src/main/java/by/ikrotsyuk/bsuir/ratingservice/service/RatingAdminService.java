package by.ikrotsyuk.bsuir.ratingservice.service;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import org.springframework.data.domain.Page;

public interface RatingAdminService {
    RatingResponseDTO getRatingById(Long id);
    Page<RatingResponseDTO> getAllRatings();
    RatingResponseDTO editRating(Long id, RatingRequestDTO requestDTO);
    RatingResponseDTO deleteRating(Long id);
}
