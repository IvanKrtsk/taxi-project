package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.IdIsNotValidException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewNotFoundByIdException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewsNotFoundException;
import by.ikrotsyuk.bsuir.ratingservice.mapper.RatingMapper;
import by.ikrotsyuk.bsuir.ratingservice.repository.RatingRepository;
import by.ikrotsyuk.bsuir.ratingservice.service.utils.PaginationUtil;
import by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REVIEW_INVALID_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.getObjectsPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingAdminServiceImplTest {
    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private PaginationUtil paginationUtil;

    @InjectMocks
    private RatingAdminServiceImpl ratingAdminServiceImpl;

    private final RatingAdminResponseDTO ratingAdminResponseDTO = TestDataGenerator.getRatingAdminResponseDTO();
    private final RatingEntity ratingEntity = TestDataGenerator.getRatingEntity();
    private final RatingRequestDTO ratingRequestDTO = TestDataGenerator.getRatingRequestDTO();
    private final Pageable pageRequest = TestDataGenerator.getPageRequest();

    @Test
    void getReviewById_ReturnsRatingAdminResponseDTO() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(ratingEntity));
        when(ratingMapper.toAdminDTO(any(RatingEntity.class)))
                .thenReturn(ratingAdminResponseDTO);

        var dto = ratingAdminServiceImpl.getReviewById(String.valueOf(ratingAdminResponseDTO.id()));

        assertThat(dto)
                .isNotNull()
                .isEqualTo(ratingAdminResponseDTO);

        verify(ratingRepository)
                .findById(any(ObjectId.class));
        verify(ratingMapper)
                .toAdminDTO(any(RatingEntity.class));
    }

    @Test
    void getReviewById_ThrowsIdIsNotValidException() {
        assertThatThrownBy(() ->
                ratingAdminServiceImpl.getReviewById(REVIEW_INVALID_ID)
        ).isInstanceOf(IdIsNotValidException.class);
    }

    @Test
    void getReviewById_ThrowsReviewNotFoundByIdException() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                ratingAdminServiceImpl.getReviewById(NON_EXISTENT_ID.toString())
        ).isInstanceOf(ReviewNotFoundByIdException.class);
    }


    @Test
    void getAllReviews_ReturnsPageOfRatingAdminResponseDTO() {
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(pageRequest);
        when(ratingRepository.findAll(any(Pageable.class)))
                .thenReturn(getObjectsPage(ratingEntity));
        when(ratingMapper.toAdminDTO(any(RatingEntity.class)))
                .thenAnswer(invocation -> ratingAdminResponseDTO);

        Page<RatingAdminResponseDTO> result = ratingAdminServiceImpl.getAllReviews(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().rating())
                .isEqualTo(ratingAdminResponseDTO.rating());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(ratingRepository)
                .findAll(any(Pageable.class));
        verify(ratingMapper)
                .toAdminDTO(any(RatingEntity.class));
    }

    @Test
    void getAllReviews_ThrowsReviewsNotFoundException() {
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(pageRequest);
        when(ratingRepository.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                ratingAdminServiceImpl.getAllReviews(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(ReviewsNotFoundException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(ratingRepository)
                .findAll(any(Pageable.class));
    }

    @Test
    void editReview_ReturnsRatingAdminResponseDTO() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(ratingEntity));
        when(ratingRepository.save(any(RatingEntity.class)))
                .thenReturn(ratingEntity);
        when(ratingMapper.toAdminDTO(any(RatingEntity.class)))
                .thenReturn(ratingAdminResponseDTO);

        var dto = ratingAdminServiceImpl.editReview(String.valueOf(ratingEntity.getId()), ratingRequestDTO);

        assertThat(dto)
                .isNotNull()
                .isEqualTo(ratingAdminResponseDTO);

        verify(ratingRepository)
                .findById(any(ObjectId.class));
        verify(ratingRepository)
                .save(any(RatingEntity.class));
        verify(ratingMapper)
                .toAdminDTO(any(RatingEntity.class));
    }

    @Test
    void editReview_ThrowsIdIsNotValidException() {
        assertThatThrownBy(() ->
                ratingAdminServiceImpl.editReview(REVIEW_INVALID_ID, ratingRequestDTO)
        ).isInstanceOf(IdIsNotValidException.class);
    }

    @Test
    void editReview_ThrowsReviewNotFoundByIdException() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                ratingAdminServiceImpl.editReview(ratingEntity.getId().toString(), ratingRequestDTO)
        ).isInstanceOf(ReviewNotFoundByIdException.class);
    }

    @Test
    void deleteReview_ReturnsRatingAdminResponseDTO() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(ratingEntity));
        when(ratingMapper.toAdminDTO(any(RatingEntity.class)))
                .thenReturn(ratingAdminResponseDTO);
        doNothing()
                .when(ratingRepository).deleteById(any(ObjectId.class));

        var dto = ratingAdminServiceImpl.deleteReview(ratingEntity.getId().toString());

        assertThat(dto)
                .isNotNull()
                .isEqualTo(ratingAdminResponseDTO);
    }

    @Test
    void deleteReview_ThrowsIdIsNotValidException() {
        assertThatThrownBy(() ->
                ratingAdminServiceImpl.deleteReview(REVIEW_INVALID_ID)
        ).isInstanceOf(IdIsNotValidException.class);
    }

    @Test
    void deleteReview_ThrowsReviewNotFoundByIdException() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                ratingAdminServiceImpl.deleteReview(NON_EXISTENT_ID.toString())
        ).isInstanceOf(ReviewNotFoundByIdException.class);
    }
}
