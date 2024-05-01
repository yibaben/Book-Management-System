package com.mobilise.BookManagementSystem.service;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
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

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookServices {
    private final BookRepository bookRepository;
    private final BookInfoValidations bookInfoValidations;
    private final BookMapper bookMapper;
    private final ModelMapper modelMapper;

    /**
     * This is a method that adds a new book to a library.
     * It takes a BookRequest object as input, which contains information about the new book such as title, author, and publication year etc.
     * The method performs input validations and throws specific exceptions if any of the validations fail.
     * If the validations pass, it maps the BookRequest object to a BookLibrary entity, saves it to the database,
     * and then maps the BookLibrary entity to a BookResponse object. Finally, it logs a success message and returns the BookResponse object.
     * If any exceptions occur during the process, it throws a BookCreationException with an error message.
     * @param  bookRequest   the request object containing information about the new book
     *                       including title, author, and publication year etc
     * @return               the response object containing information about the newly added book
     */
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

    /**
     * This is a method that retrieves a paginated list of books from a repository.
     * It takes two parameters: pageNo and pageSize, which determine the page number and size of the pagination.
     * Inside the method, it creates a Pageable object using the pageNo and pageSize parameters.
     * Then it uses the findAll method of the bookRepository to retrieve a Page of BookLibrary objects based on the Pageable object.
     * After that, it maps each BookLibrary object to a BookResponse object using a bookMapper and collects them into a list.
     * Finally, it builds and returns a PaginatedBookResponse object containing the mapped list, the number of elements per page, and the total page size.
     * If any exception occurs during the process, it logs an error message and throws a NotFoundException with the error message.
     * @param  pageNo      the page number to retrieve
     * @param  pageSize    the number of books per page
     * @return             the paginated book response
     */
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
            log.error("Error occurred while retrieving all Books with pagination: " + e.getMessage());
            throw new NotFoundException("Error Occurred while retrieving Book List: " + e.getMessage());
        }
    }

    /**
     * This is the getBookById method that retrieves a book from a library by its unique ID.
     * It first tries to find the book using the ID from the bookRepository.
     * If the book is not found, it throws a NotFoundException.
     * If an exception occurs during the process, it logs the error and throws a NotFoundException with the error message.
     *
     * @param  id    the request parameter for the unique ID of the book
     * @return       the response object containing information about the retrieved book
     */
    @Override
    public BookResponse getBookById(Long id) {
        try {
            // Retrieve Book entity by UniqueId
            BookLibrary book = bookRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Book with id " + id + " does not exist"));
            // Map and return BookResponse
            return bookMapper.mapBookLibraryToBookResponse(book);
        } catch (Exception ex) {
            // Log the specific exception details
            log.error("Error while retrieving Book: {}", ex.getMessage());
            throw new NotFoundException("Error Occurred while retrieving Book: " + ex.getMessage());
        }
    }

    /**
     * This is a method that searches for books by title, author, ISBN, or publication year in a book library.
     * It first tries to parse the search text as a year and checks if the year exists in the database.
     * If it does, it retrieves the books by the publication year; otherwise, it searches by title, author, and ISBN.
     * It then maps the results to a list of BookResponse objects and returns them.
     * If no books are found, it throws a NotFoundException.
     *
     * @param  searchText    the text to search for in the database
     * @return              a list of BookResponse objects containing the search results
     */
    @Override
    public List<BookResponse> searchBookByTitleOrAuthorOrIsbnOrPublisherYear(String searchText) {
        try {
            // Retrieve Book entity by SearchText
            List<BookLibrary> book;
            Year publicationYear = null;
            try {
                publicationYear = Year.parse(searchText);
                // Check if the publicationYear exists in the database
                boolean yearExists = bookRepository.existsByPublicationYear(publicationYear);
                if (yearExists) {
                    // Handling cases where searchText is a valid Year
                    book = bookRepository.searchByTitleOrAuthorOrIsbnOrPublicationYear(null, null, null, publicationYear);
                } else {
                    // Throw exception if publicationYear does not exist in the database
                    throw new NotFoundException("Book with search text " + searchText + " does not exist");
                }
            } catch (Exception e) {
                // Handling cases where searchText is not a valid Year
                book = bookRepository.searchByTitleOrAuthorOrIsbnOrPublicationYear(searchText, searchText, searchText, null);
            }
//            List<BookLibrary> book = bookRepository.searchByTitleOrAuthorOrIsbnOrPublicationYear(searchText, searchText, searchText, Year.parse(searchText));
            List<BookResponse> bookResponses = new ArrayList<>();
            for (BookLibrary bookLibrary : book) {
                BookResponse response = modelMapper.map(bookLibrary, BookResponse.class);
                bookResponses.add(response);
            }
            if (!bookResponses.isEmpty()) {
                log.info("Book successfully retrieved from search text: " + searchText);
                return bookResponses;
            }
            else {
                throw new NotFoundException("Book with search text " + searchText + " does not exist");
            }
        }catch (Exception ex) {
            // Log the specific exception details
            log.error("Error while searching Book: {}", ex.getMessage());
            throw new NotFoundException("Error Occurred while searching Book: " + ex.getMessage());
        }
    }

    /**
     * This is a method that updates a book in a book library system.
     * It first checks if the book exists in the repository,
     * validates the input data, updates the book entity with new values,
     * saves the updated book, and returns a response with the updated book details.
     * If the book does not exist, it throws a NotFoundException.
     * Any errors that occur during the update process are logged and a NotFoundException is thrown with the error message.
     * @param  id           the ID of the book to be updated
     * @param  bookRequest  the details of the book to update
     * @return             the response containing the updated book details
     */
    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        try {
            Optional<BookLibrary> existingBook = bookRepository.findById(id);
            if (existingBook.isPresent()) {
                BookLibrary updatedBook = existingBook.get();
                // Validate inputs
                bookInfoValidations.isBookTitleAlreadyExists(bookRequest.getTitle());
                // Update BookLibrary entity with new values
                if (bookRequest.getTitle() != null) {
                    bookInfoValidations.validateTitle(bookRequest.getTitle());
                    updatedBook.setTitle(bookRequest.getTitle());
                }
                if (bookRequest.getAuthor() != null) {
                    bookInfoValidations.validateAuthor(bookRequest.getAuthor());
                    updatedBook.setAuthor(bookRequest.getAuthor());
                }
                if (bookRequest.getIsbn() != null) {
                    updatedBook.setIsbn(bookRequest.getIsbn());
                }
                if (bookRequest.getPublicationYear() != null) {
                    bookInfoValidations.validatePublicationYear(bookRequest.getPublicationYear());
                    updatedBook.setPublicationYear(bookRequest.getPublicationYear());
                }
                if (bookRequest.getQuantity() != null) {
                    updatedBook.setQuantity(bookRequest.getQuantity());
                }
                // Save Updated Book entity, Map and return Updated BookResponse
                BookLibrary savedBook = bookRepository.save(updatedBook);
                BookResponse updatedResponse = modelMapper.map(savedBook, BookResponse.class);
                log.info("Book successfully updated");
                return updatedResponse;
            }else {
                throw new NotFoundException("Book with id " + id + " not found and cannot be updated");
            }
        }catch (Exception ex) {
            // Log the specific exception details
            log.error("Error while updating Book: {}", ex.getMessage());
            throw new NotFoundException("Error Occurred while updating Book: " + ex.getMessage());
        }
    }

    /**
     * This implementation defines a method that deletes a book by its unique ID.
     * If the deletion is successful, it logs the deletion success status and returns an empty ApiResponse object.
     * If there is an error, it logs the exception details and throws a custom NotFoundException with the error message.
     * @param  id  the unique ID of the book to be deleted
     * @return     an ApiResponse object indicating the success of the operation
     */
    @Override
    public ApiResponse deleteBookById(Long id) {
        try {
            // Delete Book entity by UniqueId
            bookRepository.deleteById(id);
            log.info("Book successfully deleted with id: " + id);
        } catch (Exception ex) {
            // Log the specific exception details
            log.error("Error while deleting Book: {}", ex.getMessage());
            throw new NotFoundException("Error Occurred while deleting Book: " + ex.getMessage());
        }
        return new ApiResponse();
    }
}
