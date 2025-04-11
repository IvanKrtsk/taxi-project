package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.IdIsNotValidException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.ReviewNotFoundByIdException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.ReviewsNotFoundException;
import by.ikrotsyuk.bsuir.ratingservice.mapper.RatingMapper;
import by.ikrotsyuk.bsuir.ratingservice.repository.RatingRepository;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingReviewerService;
import by.ikrotsyuk.bsuir.ratingservice.service.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class RatingReviewerServiceImpl implements RatingReviewerService {
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;
    private final PaginationUtil paginationUtil;

    @Override
    public RatingResponseDTO leaveReview(RatingRequestDTO ratingRequestDTO) {
        RatingEntity ratingEntity = ratingMapper.toEntity(ratingRequestDTO);
        Date now = Date.from(Instant.now());
        ratingEntity.setCreatedAt(now);
        ratingEntity.setUpdatedAt(now);
        return ratingMapper.toDTO(
                ratingRepository.save(ratingEntity));
    }

    @Override
    public Page<RatingResponseDTO> viewLeavedReviews(Long reviewerId, ReviewerTypesRating reviewerType, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        Page<RatingEntity> ratingEntities = ratingRepository
                .findAllByReviewerIdAndReviewerType(reviewerId, reviewerType,
                        paginationUtil.getPageRequest(offset, itemCount, field, isSortDirectionAsc));
        if(!ratingEntities.hasContent())
            throw new ReviewsNotFoundException();
        return ratingEntities.map(ratingMapper::toDTO);
    }

    @Override
    public RatingResponseDTO getReviewById(String reviewId) {
        if (!ObjectId.isValid(reviewId))
            throw new IdIsNotValidException(reviewId);
        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new ReviewNotFoundByIdException(reviewId));

        return ratingMapper.toDTO(ratingEntity);
    }
}
