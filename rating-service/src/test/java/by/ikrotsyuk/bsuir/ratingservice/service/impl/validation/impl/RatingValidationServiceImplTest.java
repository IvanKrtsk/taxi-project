package by.ikrotsyuk.bsuir.ratingservice.service.impl.validation.impl;

import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.FeignConnectException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotAcceptedException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToDriverException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToPassengerException;
import by.ikrotsyuk.bsuir.ratingservice.feign.RideClient;
import by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingValidationServiceImplTest {
    @Mock
    private RideClient rideClient;

    @InjectMocks
    private RatingValidationServiceImpl ratingValidationServiceImpl;

    private final RideFullResponseDTO rideFullResponseDTO = TestDataGenerator.getRideFullResponseDTO();
    private final RideFullResponseDTO rideFullResponseDTOWithNullDriverId = TestDataGenerator.getRideFullResponseDTOWithNullDriverId();
    private final RideFullResponseDTO rideFullResponseDTOWithCustomPassengerId = TestDataGenerator.getRideFullResponseDTOWithCustomPassengerId();
    private final RideFullResponseDTO rideFullResponseDTOWithCustomDriverId = TestDataGenerator.getRideFullResponseDTOWithCustomDriverId();
    private final RatingRequestDTO ratingRequestDTO = TestDataGenerator.getRatingRequestDTO();

    @Test
    void checkIdMatch_ThrowsRideNotAcceptedException() {
        assertThatThrownBy(() ->
                ratingValidationServiceImpl.checkIdMatch(rideFullResponseDTOWithNullDriverId, ratingRequestDTO, ReviewerTypes.PASSENGER)
        ).isInstanceOf(RideNotAcceptedException.class);
    }

    @Test
    void checkIdMatch_ThrowsRideNotBelongToPassengerException() {
        assertThatThrownBy(() ->
                ratingValidationServiceImpl.checkIdMatch(rideFullResponseDTOWithCustomPassengerId, ratingRequestDTO, ReviewerTypes.PASSENGER)
        ).isInstanceOf(RideNotBelongToPassengerException.class);
    }

    @Test
    void checkIdMatch_ThrowsRideNotBelongToDriverException() {
        assertThatThrownBy(() ->
                ratingValidationServiceImpl.checkIdMatch(rideFullResponseDTOWithCustomDriverId, ratingRequestDTO, ReviewerTypes.PASSENGER)
        ).isInstanceOf(RideNotBelongToDriverException.class);
    }

    @Test
    void getRideDTO_ReturnsRideFullResponseDTO() {
        when(rideClient.getRideById(anyLong()))
                .thenReturn(ResponseEntity.ok(rideFullResponseDTO));

        var dto = ratingValidationServiceImpl.getRideDTO(rideFullResponseDTO.id());

        assertThat(dto)
                .isNotNull()
                .isEqualTo(rideFullResponseDTO);

        verify(rideClient)
                .getRideById(anyLong());
    }

    @Test
    void getRideDTO_ThrowsRideFullResponseDTO() {
        when(rideClient.getRideById(anyLong()))
                .thenThrow(FeignConnectException.class);

        assertThatThrownBy(() ->
                ratingValidationServiceImpl.getRideDTO(rideFullResponseDTO.id())
        ).isInstanceOf(FeignConnectException.class);
    }
}
