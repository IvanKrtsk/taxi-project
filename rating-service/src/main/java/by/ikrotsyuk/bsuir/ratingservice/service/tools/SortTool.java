package by.ikrotsyuk.bsuir.ratingservice.service.tools;

import org.springframework.data.domain.Sort;

public class SortTool {
    public static Sort getSort(String field, Boolean isSortDirectionAsc){
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(sortDirection, field);
    }
}
