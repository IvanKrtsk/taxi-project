package by.ikrotsyuk.bsuir.ratingservice.service.utils;

import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class PaginationUtil {
    private final Set<String> entityFields = new HashSet<>();
    private final String DEFAULT_RATING_SORT_FIELD = RatingEntity.Fields.id.name();

    @PostConstruct
    protected void init(){
        for(RatingEntity.Fields field: RatingEntity.Fields.values())
            entityFields.add(field.name());
    }

    public Sort getSort(String field, Boolean isSortDirectionAsc){
        if(Objects.isNull(field) || field.isBlank() || !entityFields.contains(field))
            field = DEFAULT_RATING_SORT_FIELD;
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
