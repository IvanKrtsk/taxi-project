package by.ikrotsyuk.bsuir.driverservice.service.tools;

import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Component
public class PaginationTool {
    private final Set<String> driverEntityFields = new HashSet<>();
    private final Set<String> vehicleEntityFields = new HashSet<>();
    private static final String DEFAULT_SORT_FIELD = "id";


    @PostConstruct
    protected void init(){
        for(Field field: DriverEntity.class.getDeclaredFields())
            driverEntityFields.add(field.getName());

        for(Field field: VehicleEntity.class.getDeclaredFields())
            vehicleEntityFields.add(field.getName());
    }

    public Sort getSort(String field, Boolean isSortDirectionAsc, Class<?> clazz){
        if(StringUtils.isBlank(field))
            field = DEFAULT_SORT_FIELD;
        else
            if (clazz == DriverEntity.class && !driverEntityFields.contains(field)) {
                field = DEFAULT_SORT_FIELD;
            } else if (clazz == VehicleEntity.class && !vehicleEntityFields.contains(field)) {
                field = DEFAULT_SORT_FIELD;
            }

        if(isSortDirectionAsc == null)
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