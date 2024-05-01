package com.mobilise.BookManagementSystem.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedBookResponse {
    private List<BookResponse> contents;
    private int pageElementCount;
    private int pageSize;
}
