package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypesRating;
import by.ikrotsyuk.bsuir.ratingservice.mapper.RatingMapper;
import by.ikrotsyuk.bsuir.ratingservice.repository.RatingRepository;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingReviewerService;
import by.ikrotsyuk.bsuir.ratingservice.service.tools.SortTool;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class RatingReviewerServiceImpl implements RatingReviewerService {
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;

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
                        PageRequest.of(offset, itemCount,
                                SortTool.getSort(field, isSortDirectionAsc)));
        if(!ratingEntities.hasContent())
            throw new RuntimeException("ex");
        return ratingEntities.map(ratingMapper::toDTO);
    }

    @Override
    public RatingResponseDTO getReviewById(String reviewId) {
        if (!ObjectId.isValid(reviewId))
            throw new RuntimeException("ex");
        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new RuntimeException("ex"));

        return ratingMapper.toDTO(ratingEntity);
    }
}
