package by.ikrotsyuk.bsuir.ratingservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.FeignConnectException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.IdIsNotValidException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewAlreadyExistsException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewNotFoundByIdException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewsNotFoundException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotAcceptedException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToDriverException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToPassengerException;
import by.ikrotsyuk.bsuir.ratingservice.kafka.producer.RatingProducer;
import by.ikrotsyuk.bsuir.ratingservice.mapper.RatingMapper;
import by.ikrotsyuk.bsuir.ratingservice.repository.RatingRepository;
import by.ikrotsyuk.bsuir.ratingservice.service.impl.validation.RatingValidationService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingReviewerServiceImplTest {
    @Mock
    private RatingMapper ratingMapper;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private PaginationUtil paginationUtil;

    @Mock
    private RatingProducer ratingProducer;

    @Mock
    private RatingValidationService ratingValidationService;

    @InjectMocks
    private RatingReviewerServiceImpl ratingReviewerServiceImpl;

    private final RatingRequestDTO ratingRequestDTO = TestDataGenerator.getRatingRequestDTO();
    private final RatingEntity ratingEntity = TestDataGenerator.getRatingEntity();
    private final RatingResponseDTO ratingResponseDTO = TestDataGenerator.getRatingResponseDTO();
    private final RideFullResponseDTO rideFullResponseDTO = TestDataGenerator.getRideFullResponseDTO();
    private final Pageable pageRequest = TestDataGenerator.getPageRequest();

    @Test
    void leaveReview_ReturnsRatingResponseDTO() {
        when(ratingRepository.existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class)))
                .thenReturn(false);
        when(ratingValidationService.getRideDTO(anyLong()))
                .thenReturn(rideFullResponseDTO);
        doNothing()
                .when(ratingValidationService).checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));
        when(ratingMapper.toEntity(any(RatingRequestDTO.class)))
                .thenReturn(ratingEntity);
        when(ratingRepository.save(any(RatingEntity.class)))
                .thenReturn(ratingEntity);
        doNothing()
                .when(ratingProducer).sendRatingUpdatedEvent(any(ObjectId.class), any(RatingUpdatedEvent.class), any(ReviewerTypes.class));
        when(ratingMapper.toDTO(any(RatingEntity.class)))
                .thenReturn(ratingResponseDTO);

        var dto = ratingReviewerServiceImpl.leaveReview(ratingRequestDTO);

        assertThat(dto)
                .isNotNull()
                .isEqualTo(ratingResponseDTO);

        verify(ratingRepository)
                .existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class));
        verify(ratingValidationService)
                .getRideDTO(anyLong());
        verify(ratingValidationService)
                .checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));
        verify(ratingMapper)
                .toEntity(any(RatingRequestDTO.class));
        verify(ratingRepository, times(2))
                .save(any(RatingEntity.class));
        verify(ratingProducer)
                .sendRatingUpdatedEvent(any(ObjectId.class), any(RatingUpdatedEvent.class), any(ReviewerTypes.class));
        verify(ratingMapper)
                .toDTO(any(RatingEntity.class));
    }

    @Test
    void leaveReview_ThrowsReviewAlreadyExistsException() {
        when(ratingRepository.existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class)))
                .thenReturn(true);

        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.leaveReview(ratingRequestDTO)
        ).isInstanceOf(ReviewAlreadyExistsException.class);

        verify(ratingRepository)
                .existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class));
    }

    @Test
    void leaveReview_ThrowsFeignConnectException() {
        when(ratingRepository.existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class)))
                .thenReturn(false);
        when(ratingValidationService.getRideDTO(anyLong()))
                .thenThrow(new FeignConnectException());

        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.leaveReview(ratingRequestDTO)
        ).isInstanceOf(FeignConnectException.class);

        verify(ratingRepository)
                .existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class));
        verify(ratingValidationService)
                .getRideDTO(anyLong());
    }

    @Test
    void leaveReview_ThrowsRideNotAcceptedException() {
        when(ratingRepository.existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class)))
                .thenReturn(false);
        when(ratingValidationService.getRideDTO(anyLong()))
                .thenReturn(rideFullResponseDTO);
        doThrow(new RideNotAcceptedException(rideFullResponseDTO.id()))
                .when(ratingValidationService)
                .checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));

        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.leaveReview(ratingRequestDTO)
        ).isInstanceOf(RideNotAcceptedException.class);

        verify(ratingRepository)
                .existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class));
        verify(ratingValidationService)
                .getRideDTO(anyLong());
        verify(ratingValidationService)
                .checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));
    }

    @Test
    void leaveReview_ThrowsRideNotBelongToPassengerException() {
        when(ratingRepository.existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class)))
                .thenReturn(false);
        when(ratingValidationService.getRideDTO(anyLong()))
                .thenReturn(rideFullResponseDTO);
        doThrow(new RideNotBelongToPassengerException(rideFullResponseDTO.id(), rideFullResponseDTO.passengerId()))
                .when(ratingValidationService)
                .checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));

        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.leaveReview(ratingRequestDTO)
        ).isInstanceOf(RideNotBelongToPassengerException.class);

        verify(ratingRepository)
                .existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class));
        verify(ratingValidationService)
                .getRideDTO(anyLong());
        verify(ratingValidationService)
                .checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));
    }

    @Test
    void leaveReview_ThrowsRideNotBelongToDriverException() {
        when(ratingRepository.existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class)))
                .thenReturn(false);
        when(ratingValidationService.getRideDTO(anyLong()))
                .thenReturn(rideFullResponseDTO);
        doThrow(new RideNotBelongToDriverException(rideFullResponseDTO.id(), rideFullResponseDTO.driverId()))
                .when(ratingValidationService)
                .checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));

        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.leaveReview(ratingRequestDTO)
        ).isInstanceOf(RideNotBelongToDriverException.class);

        verify(ratingRepository)
                .existsByRideIdAndReviewerType(anyLong(), any(ReviewerTypes.class));
        verify(ratingValidationService)
                .getRideDTO(anyLong());
        verify(ratingValidationService)
                .checkIdMatch(any(RideFullResponseDTO.class), any(RatingRequestDTO.class), any(ReviewerTypes.class));
    }

    @Test
    void viewLeavedReviews_ReturnsPageOfRatingResponseDTO() {
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(pageRequest);
        when(ratingRepository.findAllByReviewerIdAndReviewerType(anyLong(), any(ReviewerTypes.class), any(Pageable.class)))
                .thenReturn(getObjectsPage(ratingEntity));
        when(ratingMapper.toDTO(any(RatingEntity.class)))
                .thenAnswer(invocation -> ratingResponseDTO);

        var result = ratingReviewerServiceImpl.viewLeavedReviews(ratingRequestDTO.reviewerId(), ratingResponseDTO.reviewerType(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().id())
                .isEqualTo(ratingResponseDTO.id());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(ratingRepository)
                .findAllByReviewerIdAndReviewerType(anyLong(), any(ReviewerTypes.class), any(Pageable.class));
        verify(ratingMapper)
                .toDTO(any(RatingEntity.class));
    }

    @Test
    void viewLeavedReviews_ThrowsReviewsNotFoundException() {
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(pageRequest);
        when(ratingRepository.findAllByReviewerIdAndReviewerType(anyLong(), any(ReviewerTypes.class), any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.viewLeavedReviews(ratingRequestDTO.reviewerId(), ratingResponseDTO.reviewerType(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(ReviewsNotFoundException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(ratingRepository)
                .findAllByReviewerIdAndReviewerType(anyLong(), any(ReviewerTypes.class), any(Pageable.class));
    }

    @Test
    void getReviewById_ReturnsRatingResponseDTO() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.of(ratingEntity));
        when(ratingMapper.toDTO(any(RatingEntity.class)))
                .thenReturn(ratingResponseDTO);

        var dto = ratingReviewerServiceImpl.getReviewById(ratingResponseDTO.id().toString());

        assertThat(dto)
                .isNotNull()
                .isEqualTo(ratingResponseDTO);

        verify(ratingRepository)
                .findById(any(ObjectId.class));
        verify(ratingMapper)
                .toDTO(any(RatingEntity.class));
    }

    @Test
    void getReviewById_ThrowsIdIsNotValidException() {
        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.getReviewById(REVIEW_INVALID_ID)
        ).isInstanceOf(IdIsNotValidException.class);
    }

    @Test
    void getReviewById_ThrowsReviewNotFoundByIdException() {
        when(ratingRepository.findById(any(ObjectId.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                ratingReviewerServiceImpl.getReviewById(NON_EXISTENT_ID.toString())
        ).isInstanceOf(ReviewNotFoundByIdException.class);
    }
}
