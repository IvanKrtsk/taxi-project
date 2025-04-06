package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingAdminOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/rating/admin")
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
