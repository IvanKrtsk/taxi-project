package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingAdminService;
import org.springframework.data.domain.Page;

public class RatingAdminServiceImpl implements RatingAdminService {

    @Override
    public RatingResponseDTO getRatingById(Long id) {
        return null;
    }

    @Override
    public Page<RatingResponseDTO> getAllRatings() {
        return null;
    }

    @Override
    public RatingResponseDTO editRating(Long id, RatingRequestDTO requestDTO) {
        return null;
    }

    @Override
    public RatingResponseDTO deleteRating(Long id) {
        return null;
    }
}
