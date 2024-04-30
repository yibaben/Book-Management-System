package com.mobilise.BookManagementSystem.service.mapper;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.entity.BookLibrary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@Slf4j
public class BookMapper {
    /**
     * Maps a BookRequest object to a BookLibrary object.
     *
     * @param  bookRequest  the BookRequest object to be mapped
     * @return              the mapped BookLibrary object
     */
    public BookLibrary mapBookRequestToBookLibrary(BookRequest bookRequest) {
        BookLibrary bookLibrary = new BookLibrary();
        bookLibrary.setTitle(bookRequest.getTitle());
        bookLibrary.setAuthor(bookRequest.getAuthor());
        bookLibrary.setIsbn(bookRequest.getIsbn());
        bookLibrary.setQuantity(bookRequest.getQuantity());
        bookLibrary.setPublicationYear(bookRequest.getPublicationYear());
        return bookLibrary;
    }

    /**
     * Maps a BookLibrary object to a BookResponse object.
     *
     * @param  bookLibrary  the BookLibrary object to map
     * @return              the resulting BookResponse object
     */
    public BookResponse mapBookLibraryToBookResponse(BookLibrary bookLibrary) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(bookLibrary.getId());
        bookResponse.setTitle(bookLibrary.getTitle());
        bookResponse.setAuthor(bookLibrary.getAuthor());
        bookResponse.setIsbn(bookLibrary.getIsbn());
        bookResponse.setQuantity(bookLibrary.getQuantity());
        bookResponse.setPublicationYear(bookLibrary.getPublicationYear());
        return bookResponse;
    }
}
