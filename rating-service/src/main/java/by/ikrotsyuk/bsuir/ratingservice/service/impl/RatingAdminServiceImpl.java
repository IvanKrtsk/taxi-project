package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.mapper.RatingMapper;
import by.ikrotsyuk.bsuir.ratingservice.repository.RatingRepository;
import by.ikrotsyuk.bsuir.ratingservice.service.RatingAdminService;
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
public class RatingAdminServiceImpl implements RatingAdminService {
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;

    @Override
    public RatingAdminResponseDTO getReviewById(String reviewId) {
        if (!ObjectId.isValid(reviewId))
            throw new RuntimeException("ex");

        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new RuntimeException("ex"));

        return ratingMapper.toAdminDTO(ratingEntity);
    }

    @Override
    public Page<RatingAdminResponseDTO> getAllReviews(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        Page<RatingEntity> ratingEntities = ratingRepository
                .findAll(PageRequest.of(offset, itemCount,
                                SortTool.getSort(field, isSortDirectionAsc)));
        if(!ratingEntities.hasContent())
            throw new RuntimeException("ex");
        return ratingEntities.map(ratingMapper::toAdminDTO);
    }

    @Override
    public RatingAdminResponseDTO editReview(String reviewId, RatingRequestDTO requestDTO) {
        if (!ObjectId.isValid(reviewId))
            throw new RuntimeException("ex");

        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new RuntimeException("ex"));

        ratingEntity.setRideId(requestDTO.rideId());
        ratingEntity.setReviewerId(requestDTO.reviewerId());
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
            throw new RuntimeException("ex");

        RatingEntity ratingEntity = ratingRepository.findById(new ObjectId(reviewId))
                .orElseThrow(() -> new RuntimeException("ex"));

        RatingAdminResponseDTO ratingResponseDTO = ratingMapper.toAdminDTO(ratingEntity);

        ratingRepository.deleteById(new ObjectId(reviewId));
        return ratingResponseDTO;
    }
}
