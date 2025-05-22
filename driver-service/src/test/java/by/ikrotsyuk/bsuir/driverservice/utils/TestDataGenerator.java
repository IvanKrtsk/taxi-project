package by.ikrotsyuk.bsuir.driverservice.utils;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.dto.*;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.StatusTypes;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public final class TestDataGenerator {
    private final Long DRIVER_ID = 1L;
    private final String DRIVER_NAME = "John Doe";
    private final String DRIVER_EMAIL = "john@gmail.com";
    private final String DRIVER_PHONE = "+5428064968";
    private final Double DRIVER_RATING = 7.5;
    private final Long DRIVER_TOTAL_RIDES = 10L;
    private final Boolean DRIVER_IS_DELETED = false;
    private final StatusTypes DRIVER_STATUS = StatusTypes.AVAILABLE;
    private final OffsetDateTime DRIVER_CREATED_AT = OffsetDateTime.now();
    private final OffsetDateTime DRIVER_UPDATED_AT = OffsetDateTime.now();
    private final List<VehicleEntity> DRIVER_DRIVER_VEHICLES = List.of();

    private final Long VEHICLE_ID = 1L;
    private final String VEHICLE_BRAND = "Audi";
    private final String VEHICLE_MODEL = "RS7";
    private final CarClassTypes VEHICLE_CLASS = CarClassTypes.BUSINESS;
    private final Long VEHICLE_RIDES_COUNT = 13L;
    private final Integer VEHICLE_YEAR = 2025;
    private final String VEHICLE_LICENSE_PLATE = "1111XM-8";
    private final String VEHICLE_COLOR = "Nardo Gray";
    private final boolean VEHICLE_IS_CURRENT = true;
    private final OffsetDateTime VEHICLE_CREATED_AT = OffsetDateTime.now();
    private final OffsetDateTime VEHICLE_UPDATED_AT = OffsetDateTime.now();

    public static final String GET_DRIVER_PROFILE_BY_ID = "/api/v1/drivers/{driverId}/profile";
    public static final String GET_DRIVER_RATING_BY_ID = "/api/v1/drivers/{driverId}/rating";
    public static final String GET_ALL_DRIVERS = "/api/v1/drivers";
    public static final String UPDATE_DRIVER_BY_ID = "/api/v1/drivers/{driverId}";
    public static final String DELETE_DRIVER_BY_ID = "/api/v1/drivers/{driverId}";
    public static final String ADD_DRIVER = "/api/v1/drivers";
    public static final String GET_DRIVER_WITH_VEHICLES = "/api/v1/drivers/{driverId}/vehicles";

    public static final String ADD_VEHICLE = "/api/v1/drivers/vehicles/{driverId}";
    public static final String UPDATE_VEHICLE = "/api/v1/drivers/vehicles//{driverId}/{vehicleId}";
    public static final String CHOOSE_CURRENT_VEHICLE = "/api/v1/drivers/vehicles/{driverId}/{vehicleId}/current";
    public static final String GET_VEHICLE_BY_ID = "/api/v1/drivers/vehicles/{vehicleId}";
    public static final String GET_ALL_VEHICLES = "/api/v1/drivers/vehicles";
    public static final String GET_ALL_VEHICLES_BY_TYPE = "/api/v1/drivers/vehicles/type";
    public static final String GET_ALL_VEHICLES_BY_YEAR = "/api/v1/drivers/vehicles/year";
    public static final String GET_ALL_VEHICLES_BY_BRAND = "/api/v1/drivers/vehicles/brand";
    public static final String GET_VEHICLE_BY_LICENSE = "/api/v1/drivers/vehicles/license";
    public static final String DELETE_VEHICLE_BY_LICENSE = "/api/v1/drivers/vehicles/{driverId}/{vehicleId}";
    public static final String GET_ALL_DRIVER_VEHICLES = "/api/v1/drivers/vehicles/{driverId}/vehicles";
    public static final String GET_DRIVER_CURRENT_VEHICLE = "/api/v1/drivers/vehicles/{driverId}/current";

    private final Long RATING_UPDATED_EVENT_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWER_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWED_ID = 1L;
    private final Double RATING_UPDATED_EVENT_RATING = 8.6;

    @Getter
    private static final int DEFAULT_PAGE = 0;
    @Getter
    private static final int DEFAULT_ITEMS_PER_PAGE_COUNT = 10;
    @Getter
    private static final String DEFAULT_SORT_FIELD = "id";
    @Getter
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static DriverEntity getDriverEntity(){
        return DriverEntity.builder()
                .id(DRIVER_ID)
                .name(DRIVER_NAME)
                .email(DRIVER_EMAIL)
                .phone(DRIVER_PHONE)
                .rating(DRIVER_RATING)
                .totalRides(DRIVER_TOTAL_RIDES)
                .isDeleted(DRIVER_IS_DELETED)
                .status(DRIVER_STATUS)
                .createdAt(DRIVER_CREATED_AT)
                .updatedAt(DRIVER_UPDATED_AT)
                .driverVehicles(DRIVER_DRIVER_VEHICLES)
                .build();
    }

    public static DriverResponseDTO getDriverResponseDTO(){
        return new DriverResponseDTO(
                DRIVER_ID,
                DRIVER_NAME,
                DRIVER_EMAIL,
                DRIVER_PHONE,
                DRIVER_RATING,
                DRIVER_TOTAL_RIDES,
                DRIVER_IS_DELETED,
                DRIVER_STATUS
        );
    }

    public static VehicleEntity getVehicleEntity(){
        return VehicleEntity.builder()
                .id(VEHICLE_ID)
                .brand(VEHICLE_BRAND)
                .model(VEHICLE_MODEL)
                .carClass(VEHICLE_CLASS)
                .ridesCount(VEHICLE_RIDES_COUNT)
                .year(VEHICLE_YEAR)
                .licensePlate(VEHICLE_LICENSE_PLATE)
                .color(VEHICLE_COLOR)
                .isCurrent(VEHICLE_IS_CURRENT)
                .createdAt(VEHICLE_CREATED_AT)
                .updatedAt(VEHICLE_UPDATED_AT)
                .build();
    }

    public static VehicleResponseDTO getVehicleResponseDTO(boolean isCurrent){
        return new VehicleResponseDTO(
                VEHICLE_ID,
                VEHICLE_BRAND,
                VEHICLE_MODEL,
                VEHICLE_CLASS,
                VEHICLE_RIDES_COUNT,
                VEHICLE_YEAR,
                VEHICLE_LICENSE_PLATE,
                VEHICLE_COLOR,
                DRIVER_ID,
                isCurrent
        );
    }

    public static VehicleRequestDTO getVehicleRequestDTO(){
        return new VehicleRequestDTO(
                VEHICLE_BRAND,
                VEHICLE_MODEL,
                VEHICLE_CLASS,
                VEHICLE_YEAR,
                VEHICLE_LICENSE_PLATE,
                VEHICLE_COLOR
        );
    }

    public static DriverRequestDTO getDriverRequestDTO(){
        return new DriverRequestDTO(
                DRIVER_NAME,
                DRIVER_EMAIL,
                DRIVER_PHONE
        );
    }

    public static DriverVehicleResponseDTO getDriverVehicleResponseDTO(List<VehicleEntity> vehicleEntityList){
        return new DriverVehicleResponseDTO(
                DRIVER_ID,
                DRIVER_NAME,
                DRIVER_EMAIL,
                DRIVER_PHONE,
                DRIVER_RATING,
                DRIVER_TOTAL_RIDES,
                DRIVER_IS_DELETED,
                DRIVER_STATUS,
                vehicleEntityList
        );
    }

    public static RatingUpdatedEvent getRatingUpdatedEvent(){
        return new RatingUpdatedEvent(
                RATING_UPDATED_EVENT_ID,
                RATING_UPDATED_EVENT_REVIEWER_ID,
                RATING_UPDATED_EVENT_REVIEWED_ID,
                RATING_UPDATED_EVENT_RATING
        );
    }

    @SafeVarargs
    public static <T> Page<T> getObjectsPage(T... entitiesArr){
        PageRequest pageable = PageRequest.of(DEFAULT_PAGE, entitiesArr.length, Sort.by(DEFAULT_SORT_FIELD));

        List<T> entities = Arrays.stream(entitiesArr)
                .toList();

        return new PageImpl<>(entities, pageable, entities.size());
    }

    public static Pageable getPageRequest(){
        return PageRequest.of(DEFAULT_PAGE, DEFAULT_ITEMS_PER_PAGE_COUNT, Sort.by(DEFAULT_SORT_DIRECTION, DEFAULT_SORT_FIELD));
    }

    public static DriverVehicleResponseDTO getDriverVehicleResponseDTO(){
        return new DriverVehicleResponseDTO(
                DRIVER_ID,
                DRIVER_NAME,
                DRIVER_EMAIL,
                DRIVER_PHONE,
                DRIVER_RATING,
                DRIVER_TOTAL_RIDES,
                DRIVER_IS_DELETED,
                DRIVER_STATUS,
                List.of(getVehicleEntity())
        );
    }
}
