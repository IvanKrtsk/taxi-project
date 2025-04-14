package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingAdminService;
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
@RequestMapping("/api/v1/ratings")
public class RatingController implements RatingOperations {
    private final RatingAdminService ratingAdminService;

    @Override
    public ResponseEntity<RatingAdminResponseDTO> getRatingById(String ratingId) {
        return new ResponseEntity<>(ratingAdminService.getReviewById(ratingId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<RatingAdminResponseDTO>> getAllRatings(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(ratingAdminService.getAllReviews(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RatingAdminResponseDTO> editRating(String ratingId, RatingRequestDTO requestDTO) {
        return new ResponseEntity<>(ratingAdminService.editReview(ratingId, requestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RatingAdminResponseDTO> deleteRating(String ratingId) {
        return new ResponseEntity<>(ratingAdminService.deleteReview(ratingId), HttpStatus.NO_CONTENT);
    }
}
