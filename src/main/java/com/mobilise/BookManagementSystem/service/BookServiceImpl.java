package com.mobilise.BookManagementSystem.service;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.dto.response.PaginatedBookResponse;
import com.mobilise.BookManagementSystem.entity.BookLibrary;
import com.mobilise.BookManagementSystem.exception.*;
import com.mobilise.BookManagementSystem.repository.BookRepository;
import com.mobilise.BookManagementSystem.service.mapper.BookMapper;
import com.mobilise.BookManagementSystem.validator.BookInfoValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public PaginatedBookResponse getAllBooksWithPagination(int pageNo, int pageSize) {
        try {
            // Retrieve a List of Books with pagination
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<BookLibrary> bookList = bookRepository.findAll(pageable);
            log.info("Book List successfully retrieved with Pagination");

            // Map and return PaginatedBookResponse
            List<BookResponse> collect = bookList.stream()
                    .map(bookMapper::mapBookLibraryToBookResponse)
                    .collect(Collectors.toList());

            return PaginatedBookResponse.builder()
                    .contents(collect)
                    .pageElementCount(bookList.getNumberOfElements())
                    .pageSize(bookList.getSize())
                    .build();
        }catch (Exception e) {
            throw new NotFoundException("Error Occurred while retrieving Book List: " + e.getMessage());
        }
    }

    @Override
    public BookResponse getBookById(Long id) {
        return null;
    }

    @Override
    public BookResponse findBookByTitleOrAuthorOrIsbn(String searchText) {
        return null;
    }

    @Override
    public BookResponse updateBook(BookRequest bookRequest) {
        return null;
    }

    @Override
    public BookResponse deleteBookById(Long id) {
        return null;
    }
}
