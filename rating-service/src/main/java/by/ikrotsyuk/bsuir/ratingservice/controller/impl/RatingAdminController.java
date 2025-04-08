package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingAdminOperations;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/ratings/admin")
public class RatingAdminController implements RatingAdminOperations {
    private final RatingAdminService ratingAdminService;

    @Override
    @GetMapping
    public ResponseEntity<RatingAdminResponseDTO> getRatingById(String id) {
        return new ResponseEntity<>(ratingAdminService.getReviewById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<RatingAdminResponseDTO>> getAllRatings(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(ratingAdminService.getAllReviews(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @PatchMapping
    public ResponseEntity<RatingAdminResponseDTO> editRating(String id, @Valid @RequestBody RatingRequestDTO requestDTO) {
        return new ResponseEntity<>(ratingAdminService.editReview(id, requestDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<RatingAdminResponseDTO> deleteRating(String id) {
        return new ResponseEntity<>(ratingAdminService.deleteReview(id), HttpStatus.OK);
    }
}
