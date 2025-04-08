package by.ikrotsyuk.bsuir.passengerservice.service.tools;

import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Component
public class PaginationTool {
    private final Set<String> entityFields = new HashSet<>();

    @PostConstruct
    protected void init(){
        for(Field field: PassengerEntity.class.getDeclaredFields())
            entityFields.add(field.getName());
    }

    public Sort getSort(String field, Boolean isSortDirectionAsc){
        if(field == null || field.isEmpty())
            field = "id";
        else
        if(!entityFields.contains(field))
            field = "id";
        if(isSortDirectionAsc == null)
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