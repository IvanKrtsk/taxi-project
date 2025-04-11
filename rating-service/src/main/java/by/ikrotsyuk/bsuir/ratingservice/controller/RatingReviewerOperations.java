package by.ikrotsyuk.bsuir.ratingservice.controller;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypeTypes;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RatingReviewerOperations {
    @PostMapping
    ResponseEntity<RatingResponseDTO> leaveReview(@Valid @RequestBody RatingRequestDTO ratingRequestDTO);

    @GetMapping("/{reviewerId}") // буду брать id из jwt и будет ресурсоориентировано
    ResponseEntity<Page<RatingResponseDTO>> viewLeavedReviews(@PathVariable Long reviewerId, @RequestParam ReviewerTypeTypes reviewerType, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @GetMapping("/rating/{ratingId}")
    ResponseEntity<RatingResponseDTO> getReviewById(@PathVariable String ratingId);
}
