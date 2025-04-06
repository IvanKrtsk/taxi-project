package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingAdminOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public class RatingAdminController implements RatingAdminOperations {

    @Override
    public ResponseEntity<RatingResponseDTO> getRatingById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Page<RatingResponseDTO>> getAllRatings() {
        return null;
    }

    @Override
    public ResponseEntity<RatingResponseDTO> editRating(Long id, RatingRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<RatingResponseDTO> deleteRating(Long id) {
        return null;
    }
}
