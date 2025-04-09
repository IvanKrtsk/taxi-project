package by.ikrotsyuk.bsuir.passengerservice.service.tools;

import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class PaginationTool {
    private final Set<String> entityFields = new HashSet<>();
    private final String DEFAULT_PASSENGERS_SORT_FIELD = PassengerEntity.Fields.id.name();

    @PostConstruct
    protected void init(){
        for(PassengerEntity.Fields field: PassengerEntity.Fields.values())
            entityFields.add(field.name());
    }

    public Sort getSort(String field, Boolean isSortDirectionAsc){
        if(field.isBlank() || !entityFields.contains(field))
            field = DEFAULT_PASSENGERS_SORT_FIELD;
        if(Objects.isNull(isSortDirectionAsc))
            isSortDirectionAsc = true;
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(sortDirection, field);
    }

    public Pageable getPageRequest(int offset, int itemCount, String field, Boolean isSortDirectionAsc){
        if(offset < 0)
            offset = 0;
        if(itemCount < 1)
            itemCount = 10;
        return PageRequest.of(offset, itemCount, getSort(field, isSortDirectionAsc));
    }
}