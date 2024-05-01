package com.mobilise.BookManagementSystem.service;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.dto.response.PaginatedBookResponse;

import java.util.List;

public interface BookServices {

    // Add
    BookResponse addNewBook(BookRequest bookRequest);
    // Retrieve with Pagination
    PaginatedBookResponse getAllBooksWithPagination(int pageNo, int pageSize);
    // Search
    BookResponse getBookById(Long id);
    // Search
    List<BookResponse> searchBookByTitleOrAuthorOrIsbnOrPublisherYear(String searchText);
    // Update
    BookResponse updateBook(Long id, BookRequest bookRequest);
    // Delete
    ApiResponse deleteBookById(Long id);

}
