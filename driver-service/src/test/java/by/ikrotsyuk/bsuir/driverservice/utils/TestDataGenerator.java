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
    private final Double DRIVER_ZERO_RATING = 0.0;
    private final Long DRIVER_ZERO_TOTAL_RIDES = 0L;

    @Getter
    private final String customDriverName = "John";
    @Getter
    private final String customDriverEmail = "johndoe@gmail.com";
    @Getter
    private final String customDriverPhone = "+123456789";

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
    private final Long VEHICLE_ZERO_RIDES_COUNT = 0L;
    private final String VEHICLE_CUSTOM_LICENSE_PLATE = "2222XM-8";

    public static final String baseUri = "http://localhost:";

    public static final String GET_DRIVER_PROFILE_BY_ID = "/api/v1/drivers/{driverId}/profile";
    public static final String GET_DRIVER_RATING_BY_ID = "/api/v1/drivers/{driverId}/rating";
    public static final String GET_ALL_DRIVERS = "/api/v1/drivers";
    public static final String UPDATE_DRIVER_BY_ID = "/api/v1/drivers/{driverId}";
    public static final String DELETE_DRIVER_BY_ID = "/api/v1/drivers/{driverId}";
    public static final String ADD_DRIVER = "/api/v1/drivers";
    public static final String GET_DRIVER_WITH_VEHICLES = "/api/v1/drivers/{driverId}/vehicles";

    public static final String ADD_VEHICLE = "/api/v1/drivers/vehicles/{driverId}";
    public static final String UPDATE_VEHICLE = "/api/v1/drivers/vehicles/{driverId}/{vehicleId}";
    public static final String CHOOSE_CURRENT_VEHICLE = "/api/v1/drivers/vehicles/{driverId}/{vehicleId}/current";
    public static final String GET_VEHICLE_BY_ID = "/api/v1/drivers/vehicles/{vehicleId}";
    public static final String GET_ALL_VEHICLES = "/api/v1/drivers/vehicles";
    public static final String GET_ALL_VEHICLES_BY_TYPE = "/api/v1/drivers/vehicles/type";
    public static final String GET_ALL_VEHICLES_BY_YEAR = "/api/v1/drivers/vehicles/year";
    public static final String GET_ALL_VEHICLES_BY_BRAND = "/api/v1/drivers/vehicles/brand";
    public static final String GET_VEHICLE_BY_LICENSE = "/api/v1/drivers/vehicles/license";
    public static final String DELETE_VEHICLE_BY_ID = "/api/v1/drivers/vehicles/{driverId}/{vehicleId}";
    public static final String GET_ALL_DRIVER_VEHICLES = "/api/v1/drivers/vehicles/{driverId}/vehicles";
    public static final String GET_DRIVER_CURRENT_VEHICLE = "/api/v1/drivers/vehicles/{driverId}/current";

    private final Long RATING_UPDATED_EVENT_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWER_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWED_ID = 1L;
    private final Double RATING_UPDATED_EVENT_RATING = 8.6;

    public static final String REQUEST_PARAM_OFFSET = "offset";
    public static final String REQUEST_PARAM_ITEMS = "itemCount";
    public static final String REQUEST_PARAM_FIELD = "field";
    public static final String REQUEST_PARAM_DIRECTION = "isSortDirectionAsc";
    public static final String REQUEST_PARAM_DIRECTION_VALUE = "true";
    public static final String REQUEST_PARAM_YEAR = "year";
    public static final String REQUEST_PARAM_TYPE = "type";
    public static final String REQUEST_PARAM_BRAND = "brand";
    public static final String REQUEST_PARAM_LICENSE_PLATE = "licensePlate";

    public static final Long NON_EXISTENT_ID = 10L;

    @Getter
    private static final int DEFAULT_PAGE = 0;
    @Getter
    private static final int DEFAULT_ITEMS_PER_PAGE_COUNT = 10;
    @Getter
    private static final String DEFAULT_SORT_FIELD = "id";
    @Getter
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static final String SQL_DELETE_DRIVERS_TABLE = "DELETE FROM drivers;";
    public static final String SQL_DELETE_VEHICLES_TABLE = "DELETE FROM vehicles;";
    public static final String SQL_RESTART_DRIVERS_SEQUENCE = "ALTER SEQUENCE drivers_id_seq RESTART WITH 1;";
    public static final String SQL_RESTART_VEHICLES_SEQUENCE = "ALTER SEQUENCE vehicles_id_seq RESTART WITH 1;";


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

    public static DriverEntity getCustomDriverEntity(String email, String phone){
        return DriverEntity.builder()
                .name(DRIVER_NAME)
                .email(email)
                .phone(phone)
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

    public static VehicleEntity getVehicleDriverEntity(DriverEntity driverEntity){
        return VehicleEntity.builder()
                .brand(VEHICLE_BRAND)
                .model(VEHICLE_MODEL)
                .carClass(VEHICLE_CLASS)
                .ridesCount(VEHICLE_RIDES_COUNT)
                .year(VEHICLE_YEAR)
                .licensePlate(VEHICLE_LICENSE_PLATE)
                .color(VEHICLE_COLOR)
                .driver(driverEntity)
                .isCurrent(VEHICLE_IS_CURRENT)
                .createdAt(VEHICLE_CREATED_AT)
                .updatedAt(VEHICLE_UPDATED_AT)
                .build();
    }

    public static VehicleEntity getIntegrationVehicleEntity(){
        return VehicleEntity.builder()
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

    public static DriverRequestDTO getCustomDriverRequestDTO(String email, String phone){
        return new DriverRequestDTO(
                getCustomDriverName(),
                email,
                phone
        );
    }

    public static DriverResponseDTO getCustomDriverResponseDTO(){
        return new DriverResponseDTO(
                DRIVER_ID,
                DRIVER_NAME,
                DRIVER_EMAIL,
                DRIVER_PHONE,
                DRIVER_ZERO_RATING,
                DRIVER_ZERO_TOTAL_RIDES,
                DRIVER_IS_DELETED,
                DRIVER_STATUS
        );
    }

    public static VehicleResponseDTO getCustomVehicleResponseDTO(){
        return new VehicleResponseDTO(
                VEHICLE_ID,
                VEHICLE_BRAND,
                VEHICLE_MODEL,
                VEHICLE_CLASS,
                VEHICLE_ZERO_RIDES_COUNT,
                VEHICLE_YEAR,
                VEHICLE_LICENSE_PLATE,
                VEHICLE_COLOR,
                DRIVER_ID,
                VEHICLE_IS_CURRENT
        );
    }

    public static VehicleResponseDTO getModifiedVehicleResponseDTO(){
        return new VehicleResponseDTO(
                VEHICLE_ID,
                VEHICLE_BRAND,
                VEHICLE_MODEL,
                VEHICLE_CLASS,
                VEHICLE_RIDES_COUNT,
                VEHICLE_YEAR,
                VEHICLE_CUSTOM_LICENSE_PLATE,
                VEHICLE_COLOR,
                DRIVER_ID,
                VEHICLE_IS_CURRENT
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

    public static VehicleRequestDTO getCustomVehicleRequestDTO(){
        return new VehicleRequestDTO(
                VEHICLE_BRAND,
                VEHICLE_MODEL,
                VEHICLE_CLASS,
                VEHICLE_YEAR,
                VEHICLE_CUSTOM_LICENSE_PLATE,
                VEHICLE_COLOR
        );
    }

    public static DriverEntity getIntegrationDriverEntity(){
        return DriverEntity.builder()
                .name(DRIVER_NAME)
                .email(DRIVER_EMAIL)
                .phone(DRIVER_PHONE)
                .status(DRIVER_STATUS)
                .totalRides(DRIVER_TOTAL_RIDES)
                .isDeleted(DRIVER_IS_DELETED)
                .rating(DRIVER_RATING)
                .build();
    }
}
