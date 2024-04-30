package com.mobilise.BookManagementSystem.service;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;

public interface BookServices {

    BookResponse addNewBook(BookRequest bookRequest);
}
