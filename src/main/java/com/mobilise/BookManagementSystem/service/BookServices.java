package com.mobilise.BookManagementSystem.service;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.dto.response.PaginatedBookResponse;

public interface BookServices {

    BookResponse addNewBook(BookRequest bookRequest);
    PaginatedBookResponse getAllBooksWithPagination(int pageNo, int pageSize);
    BookResponse getBookById(Long id);
    BookResponse findBookByTitleOrAuthorOrIsbn(String searchText);
    BookResponse updateBook(BookRequest bookRequest);
    BookResponse deleteBookById(Long id);

}
