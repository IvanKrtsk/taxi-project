package by.ikrotsyuk.bsuir.ratingservice.controller;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RatingAdminOperations {
    ResponseEntity<RatingResponseDTO> getRatingById(@RequestParam String id);
    ResponseEntity<Page<RatingResponseDTO>> getAllRatings(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<RatingResponseDTO> editRating(@RequestParam String id, @Valid @RequestBody RatingRequestDTO requestDTO);
    ResponseEntity<RatingResponseDTO> deleteRating(@RequestParam String id);
}
