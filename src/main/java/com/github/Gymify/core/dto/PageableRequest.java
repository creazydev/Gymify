package com.github.Gymify.core.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageableRequest {
    private int page;
    private int pageSize;
    private Sort.Direction direction;
    private String sort;

    public Pageable getPageable() {
        return PageRequest.of(this.page, this.pageSize, direction, sort);
    }
}
