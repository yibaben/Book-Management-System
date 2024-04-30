package com.mobilise.BookManagementSystem.service;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.entity.BookLibrary;
import com.mobilise.BookManagementSystem.exception.*;
import com.mobilise.BookManagementSystem.repository.BookRepository;
import com.mobilise.BookManagementSystem.service.mapper.BookMapper;
import com.mobilise.BookManagementSystem.validator.BookInfoValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookServices {
    private final BookRepository bookRepository;
    private final BookInfoValidations bookInfoValidations;
    private final BookMapper bookMapper;
    private final ModelMapper modelMapper;

    @Override
    public BookResponse addNewBook(BookRequest bookRequest) {
        try {
            // Validate inputs
            bookInfoValidations.requiredTitleField(bookRequest.getTitle());
            bookInfoValidations.requiredAuthorField(bookRequest.getAuthor());
            bookInfoValidations.isBookTitleAlreadyExists(bookRequest.getTitle());
            bookInfoValidations.validateTitle(bookRequest.getTitle());
            bookInfoValidations.validateAuthor(bookRequest.getAuthor());
            bookInfoValidations.validatePublicationYear(bookRequest.getPublicationYear());
            // Map and save BookLibrary entity
            BookLibrary bookLibrary = bookMapper.mapBookRequestToBookLibrary(bookRequest);
            bookRepository.save(bookLibrary);
            // Map and return BookResponse
            BookResponse responseDTO = bookMapper.mapBookLibraryToBookResponse(bookLibrary);
            log.info("New Book saved successfully");
            return responseDTO;
    }catch (AlreadyExistsException | ValidTitleException | ValidPublicationYearException
            | ValidAuthorException | TitleRequiredException | AuthorRequiredException e) {
            throw new BookCreationException("Error Occurred while Adding New Book: " + e.getMessage());
        }
    }
}
