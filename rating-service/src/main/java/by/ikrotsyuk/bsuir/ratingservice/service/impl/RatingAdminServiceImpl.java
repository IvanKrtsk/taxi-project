package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.IdIsNotValidException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.ReviewNotFoundByIdException;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions.ReviewsNotFoundException;
import by.ikrotsyuk.bsuir.ratingservice.mapper.RatingMapper;
import by.ikrotsyuk.bsuir.ratingservice.repository.RatingRepository;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingAdminService;
import by.ikrotsyuk.bsuir.ratingservice.service.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class RatingAdminServiceImpl implements RatingAdminService {
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;
    private final PaginationUtil paginationUtil;

    @Override
    public RatingAdminResponseDTO getReviewById(String reviewId) {
        if (!ObjectId.isValid(reviewId))
            throw new IdIsNotValidException(reviewId);

        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new ReviewNotFoundByIdException(reviewId));

        return ratingMapper.toAdminDTO(ratingEntity);
    }

    @Override
    public Page<RatingAdminResponseDTO> getAllReviews(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        Page<RatingEntity> ratingEntities = ratingRepository
                .findAll(paginationUtil.getPageRequest(offset, itemCount, field, isSortDirectionAsc));
        if(!ratingEntities.hasContent())
            throw new ReviewsNotFoundException();
        return ratingEntities.map(ratingMapper::toAdminDTO);
    }

    @Override
    public RatingAdminResponseDTO editReview(String reviewId, RatingRequestDTO requestDTO) {
        if (!ObjectId.isValid(reviewId))
            throw new IdIsNotValidException(reviewId);

        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new ReviewNotFoundByIdException(reviewId));

        ratingEntity.setRideId(requestDTO.rideId());
        ratingEntity.setReviewerId(requestDTO.reviewerId());
        ratingEntity.setReviewedId(requestDTO.reviewedId());
        ratingEntity.setReviewerType(requestDTO.reviewerType());
        ratingEntity.setRating(requestDTO.rating());
        ratingEntity.setComment(requestDTO.comment());
        ratingEntity.setUpdatedAt(Date.from(Instant.now()));
        ratingRepository.save(ratingEntity);

        return ratingMapper.toAdminDTO(ratingEntity);
    }

    @Override
    public RatingAdminResponseDTO deleteReview(String reviewId) {
        if (!ObjectId.isValid(reviewId))
            throw new IdIsNotValidException(reviewId);

        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new ReviewNotFoundByIdException(reviewId));

        RatingAdminResponseDTO ratingResponseDTO = ratingMapper.toAdminDTO(ratingEntity);

        ratingRepository.deleteById(new ObjectId(reviewId));
        return ratingResponseDTO;
    }
}
