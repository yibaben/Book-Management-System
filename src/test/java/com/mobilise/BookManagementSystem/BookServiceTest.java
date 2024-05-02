package com.mobilise.BookManagementSystem;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.dto.response.PaginatedBookResponse;
import com.mobilise.BookManagementSystem.entity.BookLibrary;
import com.mobilise.BookManagementSystem.exception.NotFoundException;
import com.mobilise.BookManagementSystem.exception.ValidTitleException;
import com.mobilise.BookManagementSystem.repository.BookRepository;
import com.mobilise.BookManagementSystem.service.impl.BookServiceImpl;
import com.mobilise.BookManagementSystem.service.mapper.BookMapper;
import com.mobilise.BookManagementSystem.validator.BookInfoValidations;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookServiceImpl;
    @Mock
    private BookInfoValidations bookInfoValidations;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testAddNewBook() throws ValidTitleException {
        // Set up test data
        BookRequest bookRequest = new BookRequest("Sample Title", "Sample Author", "123456789", 5, Year.of(2022));
        BookLibrary bookLibrary = new BookLibrary(1L, "Sample Title", "Sample Author", "123456789", 5, Year.of(2022));

        // Mock behavior
        given(bookMapper.mapBookRequestToBookLibrary(bookRequest)).willReturn(bookLibrary); // Mock the behavior of the bookMapper to return the bookLibrary when mapBookRequestToBookLibrary is called with the bookRequest
        given(bookRepository.save(bookLibrary)).willReturn(bookLibrary); // Mock the behavior of the bookRepository to return the bookLibrary when save is called with the bookLibrary
        given(bookMapper.mapBookLibraryToBookResponse(bookLibrary)).willReturn(new BookResponse(1L, "Sample Title", "Sample Author", "123456789", 5, Year.of(2022))); // Mock the behavior of the bookMapper to return a new BookResponse when mapBookLibraryToBookResponse is called with the bookLibrary

        // Call the method
        BookResponse response = bookServiceImpl.addNewBook(bookRequest); // Call the addNewBook method of the bookServiceImpl with the bookRequest

        // Verify the interactions and assertions
        verify(bookInfoValidations).requiredTitleField(bookRequest.getTitle()); // Verify that the requiredTitleField method of the bookInfoValidations is called with the title from the bookRequest
        verify(bookInfoValidations).requiredAuthorField(bookRequest.getAuthor()); // Verify that the requiredAuthorField method of the bookInfoValidations is called with the author from the bookRequest
        verify(bookInfoValidations).isBookTitleAlreadyExists(bookRequest.getTitle()); // Verify that the isBookTitleAlreadyExists method of the bookInfoValidations is called with the title from the bookRequest
        verify(bookInfoValidations).validateTitle(bookRequest.getTitle()); // Verify that the validateTitle method of the bookInfoValidations is called with the title from the bookRequest
        verify(bookInfoValidations).validateAuthor(bookRequest.getAuthor()); // Verify that the validateAuthor method of the bookInfoValidations is called with the author from the bookRequest
        verify(bookInfoValidations).validatePublicationYear(bookRequest.getPublicationYear()); // Verify that the validatePublicationYear method of the bookInfoValidations is called with the publicationYear from the bookRequest
        verify(bookRepository).save(bookLibrary); // Verify that the save method of the bookRepository is called with the bookLibrary
        verify(bookMapper).mapBookRequestToBookLibrary(bookRequest); // Verify that the mapBookRequestToBookLibrary method of the bookMapper is called with the bookRequest
        verify(bookMapper).mapBookLibraryToBookResponse(bookLibrary); // Verify that the mapBookLibraryToBookResponse method of the bookMapper is called with the bookLibrary
        verify(bookMapper, times(1)).mapBookRequestToBookLibrary(bookRequest); // Verify that the mapBookRequestToBookLibrary method of the bookMapper is called exactly once with the bookRequest
        verify(bookRepository, times(1)).save(bookLibrary); // Verify that the save method of the bookRepository is called exactly once with the bookLibrary

        // Assertions for the response
        assertNotNull(response); // Assert that the response is not null
        assertEquals("Sample Title", response.getTitle()); // Assert that the title of the response is "Sample Title"
        assertEquals("Sample Author", response.getAuthor()); // Assert that the author of the response is "Sample Author"
        assertEquals("123456789", response.getIsbn()); // Assert that the ISBN of the response is "123456789"
        assertEquals(5, response.getQuantity()); // Assert that the quantity of the response is 5
        assertEquals(Year.of(2022), response.getPublicationYear()); // Assert that the publication year of the response is 2022
    }

    @Test
    public void testGetAllBooksWithPagination() {
        // Set up test data
        int pageNo = 1;
        int pageSize = 10;
        Page<BookLibrary> mockPage = Mockito.mock(Page.class);

        // Mock behavior
        given(bookRepository.findAll(PageRequest.of(pageNo, pageSize))).willReturn(mockPage);
        given(mockPage.getNumberOfElements()).willReturn(0); // Set the number of elements in the mock page to 0
        given(mockPage.getSize()).willReturn(pageSize); // Set the page size of the mock page
        given(mockPage.stream()).willReturn(Stream.empty()); // Mock stream to return an empty stream

        // Call the method
        PaginatedBookResponse response = bookServiceImpl.getAllBooksWithPagination(pageNo, pageSize);

        // Verify interactions
        verify(bookRepository).findAll(PageRequest.of(pageNo, pageSize)); // Verify that findAll is called with the correct Pageable object

        // Assertions for the response
        assertNotNull(response); // Assert that the response is not null
        assertEquals(Collections.emptyList(), response.getContents()); // Assert that the contents list is empty
        assertEquals(pageSize, response.getPageSize()); // Assert that the page size matches the provided size
    }


    @Test
    public void testGetBookById() {
        // Set up test data
        Long bookId = 1L;
        BookLibrary bookLibrary = new BookLibrary(1L, "Sample Title", "Sample Author", "123456789", 5, Year.of(2022));

        // Mock behavior for successful retrieval
        given(bookRepository.findById(bookId)).willReturn(Optional.of(bookLibrary));
        given(bookMapper.mapBookLibraryToBookResponse(bookLibrary)).willReturn(new BookResponse(1L, "Sample Title", "Sample Author", "123456789", 5, Year.of(2022)));

        // Call the method
        BookResponse response = bookServiceImpl.getBookById(bookId);

        // Verify interactions
        verify(bookRepository).findById(bookId);
        verify(bookMapper).mapBookLibraryToBookResponse(bookLibrary);

        // Assertions for the response
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Sample Title", response.getTitle());
        assertEquals("Sample Author", response.getAuthor());
        assertEquals("123456789", response.getIsbn());
        assertEquals(5, response.getQuantity());
        assertEquals(Year.of(2022), response.getPublicationYear());
    }

    @Test
    public void testGetBookById_WhenBookNotFound() {
        // Set up test data
        Long bookId = 1L;

        // Mock behavior for book not found
        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        // Call the method and expect an exception
        assertThrows(NotFoundException.class, () -> bookServiceImpl.getBookById(bookId));

        // Verify interactions
        verify(bookRepository).findById(bookId);
    }


    @Test
    public void testSearchBookByTitleOrAuthorOrIsbn() {
        // Set up test data
        String searchText = "Sample";
        BookLibrary bookLibrary = new BookLibrary(1L, "Sample Title", "Sample Author", "123456789", 5, Year.of(2022));
        List<BookLibrary> bookList = Collections.singletonList(bookLibrary);

        // Mock behavior
        given(bookRepository.searchByTitleOrAuthorOrIsbn(searchText, searchText, searchText)).willReturn(bookList);
        given(modelMapper.map(bookLibrary, BookResponse.class)).willReturn(new BookResponse(1L, "Sample Title", "Sample Author", "123456789", 5, Year.of(2022)));

        // Call the method
        List<BookResponse> responses = bookServiceImpl.searchBookByTitleOrAuthorOrIsbn(searchText);

        // Verify interactions
        verify(bookRepository).searchByTitleOrAuthorOrIsbn(searchText, searchText, searchText);
        verify(modelMapper).map(bookLibrary, BookResponse.class);

        // Assertions for the response
        assertNotNull(responses); // Assert that the response list is not null
        assertEquals(1, responses.size()); // Assert that only one response is returned
        BookResponse response = responses.get(0);
        assertEquals(1L, response.getId()); // Assert the ID of the response
        assertEquals("Sample Title", response.getTitle()); // Assert the title of the response
        assertEquals("Sample Author", response.getAuthor()); // Assert the author of the response
        assertEquals("123456789", response.getIsbn()); // Assert the ISBN of the response
        assertEquals(5, response.getQuantity()); // Assert the quantity of the response
        assertEquals(Year.of(2022), response.getPublicationYear()); // Assert the publication year of the response
    }

    @Test
    public void testSearchBookByPublicationYear() {
        // Set up test data
        Year publicationYear = Year.of(2022);
        BookLibrary bookLibrary = new BookLibrary(1L, "Sample Title", "Sample Author", "123456789", 5, publicationYear);
        List<BookLibrary> bookList = Collections.singletonList(bookLibrary);

        // Mock behavior
        given(bookRepository.findAllBooksByPublicationYear(publicationYear)).willReturn(bookList);
        given(modelMapper.map(bookLibrary, BookResponse.class)).willReturn(new BookResponse(1L, "Sample Title", "Sample Author", "123456789", 5, publicationYear));

        // Call the method
        List<BookResponse> responses = bookServiceImpl.searchBookByPublicationYear(publicationYear);

        // Verify interactions
        verify(bookRepository).findAllBooksByPublicationYear(publicationYear);
        verify(modelMapper).map(bookLibrary, BookResponse.class);

        // Assertions for the response
        assertNotNull(responses); // Assert that the response list is not null
        assertEquals(1, responses.size()); // Assert that only one response is returned
        BookResponse response = responses.get(0);
        assertEquals(1L, response.getId()); // Assert the ID of the response
        assertEquals("Sample Title", response.getTitle()); // Assert the title of the response
        assertEquals("Sample Author", response.getAuthor()); // Assert the author of the response
        assertEquals("123456789", response.getIsbn()); // Assert the ISBN of the response
        assertEquals(5, response.getQuantity()); // Assert the quantity of the response
        assertEquals(publicationYear, response.getPublicationYear()); // Assert the publication year of the response
    }

    @Test
    public void testUpdateBook() {
        // Set up test data
        Long bookId = 1L;
        BookRequest bookRequest = new BookRequest("Updated Title", "Updated Author", "987654321", 3, Year.of(2023));

        // Mock behavior to return empty optional when findById is called
        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        // Call the method and assert the exception
        assertThrows(NotFoundException.class, () -> bookServiceImpl.updateBook(bookId, bookRequest));
    }

    @Test
    public void testDeleteBookById_Success() {
        // Set up test data
        Long bookId = 1L;

        // Call the method
        ApiResponse response = bookServiceImpl.deleteBookById(bookId);

        // Verify interactions
        verify(bookRepository).deleteById(bookId);

        // Assertions for the response
        assertNotNull(response); // Assert that the response is not null
        assertNull(response.getMessage()); // Assert that the message is null in case of a successful deletion
        assertEquals(HttpStatus.OK, response.getStatus()); // Assert that the status is OK for a successful deletion
        assertNotNull(response.getDateTime()); // Assert that the dateTime is not null
        assertNull(response.getData()); // Assert that the data field is null for a successful deletion
    }

    @Test
    public void testDeleteBookById_Exception() {
        // Set up test data
        Long bookId = 1L;
        String errorMessage = "Error occurred while deleting the book";

        // Mock behavior to throw an exception
        doThrow(new RuntimeException(errorMessage)).when(bookRepository).deleteById(bookId);

        // Call the method and catch the exception
        Exception exception = assertThrows(NotFoundException.class, () -> bookServiceImpl.deleteBookById(bookId));

        // Verify interactions
        verify(bookRepository).deleteById(bookId);

        // Assertions for the exception
        assertEquals("Error Occurred while deleting Book: " + errorMessage, exception.getMessage());
    }
}
