package by.ikrotsyuk.bsuir.ratingservice.controller;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface RatingOperations {
    @GetMapping("/rating/{ratingId}")
    ResponseEntity<RatingAdminResponseDTO> getRatingById(@PathVariable String ratingId);

    @GetMapping
    ResponseEntity<Page<RatingAdminResponseDTO>> getAllRatings(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @PutMapping("/{ratingId}")
    ResponseEntity<RatingAdminResponseDTO> editRating(@PathVariable String ratingId, @Valid @RequestBody RatingRequestDTO requestDTO);

    @DeleteMapping("/{ratingId}")
    ResponseEntity<RatingAdminResponseDTO> deleteRating(@PathVariable String ratingId);
}
