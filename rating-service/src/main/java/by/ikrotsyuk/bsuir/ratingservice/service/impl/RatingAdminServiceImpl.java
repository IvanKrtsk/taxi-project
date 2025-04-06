package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingAdminService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RatingAdminServiceImpl implements RatingAdminService {

    @Override
    public RatingResponseDTO getRatingById(Long id) {
        return null;
    }

    @Override
    public Page<RatingResponseDTO> getAllRatings(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
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
