package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.controller.RatingAdminOperations;
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
@RequestMapping("/api/v1/rating/admin")
public class RatingAdminController implements RatingAdminOperations {
    private final RatingAdminService ratingAdminService;

    @Override
    @GetMapping
    public ResponseEntity<RatingResponseDTO> getRatingById(Long id) {
        return new ResponseEntity<>(ratingAdminService.getRatingById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<RatingResponseDTO>> getAllRatings(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(ratingAdminService.getAllRatings(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @PatchMapping
    public ResponseEntity<RatingResponseDTO> editRating(Long id, @Valid @RequestBody RatingRequestDTO requestDTO) {
        return new ResponseEntity<>(ratingAdminService.editRating(id, requestDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<RatingResponseDTO> deleteRating(Long id) {
        return new ResponseEntity<>(ratingAdminService.deleteRating(id), HttpStatus.OK);
    }
}
