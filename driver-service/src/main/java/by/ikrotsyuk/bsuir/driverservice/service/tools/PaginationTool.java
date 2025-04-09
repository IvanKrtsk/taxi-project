package by.ikrotsyuk.bsuir.driverservice.service.tools;

import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class PaginationTool {
    private final Set<String> driverEntityFields = new HashSet<>();
    private final Set<String> vehicleEntityFields = new HashSet<>();
    private static final String DEFAULT_DRIVERS_SORT_FIELD = DriverEntity.Fields.id.name();
    private static final String DEFAULT_VEHICLES_SORT_FIELD = DriverEntity.Fields.id.name();


    @PostConstruct
    protected void init(){
        for(DriverEntity.Fields field: DriverEntity.Fields.values())
            driverEntityFields.add(field.name());

        for(VehicleEntity.Fields field: VehicleEntity.Fields.values())
            vehicleEntityFields.add(field.name());
    }

    public Sort getSort(String field, Boolean isSortDirectionAsc, Class<?> clazz){
        if(StringUtils.isBlank(field)) {
            field = (clazz == DriverEntity.class) ? DEFAULT_DRIVERS_SORT_FIELD : DEFAULT_VEHICLES_SORT_FIELD;
        } else
            if (clazz == DriverEntity.class && !driverEntityFields.contains(field)) {
                field = DEFAULT_DRIVERS_SORT_FIELD;
            } else if (clazz == VehicleEntity.class && !vehicleEntityFields.contains(field)) {
                field = DEFAULT_VEHICLES_SORT_FIELD;
            }

        if(Objects.isNull(isSortDirectionAsc))
            isSortDirectionAsc = true;
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(sortDirection, field);
    }

    public Pageable getPageRequest(int offset, int itemCount, String field, Boolean isSortDirectionAsc, Class<?> clazz){
        if(offset < 0)
            offset = 0;
        if(itemCount < 1)
            itemCount = 10;
        return PageRequest.of(offset, itemCount, getSort(field, isSortDirectionAsc, clazz));
    }
}