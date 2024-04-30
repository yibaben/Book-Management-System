package com.mobilise.BookManagementSystem.validator;

import com.mobilise.BookManagementSystem.entity.BookLibrary;
import com.mobilise.BookManagementSystem.exception.AlreadyExistsException;
import com.mobilise.BookManagementSystem.exception.ValidAuthorException;
import com.mobilise.BookManagementSystem.exception.ValidPublicationYearException;
import com.mobilise.BookManagementSystem.exception.ValidTitleException;
import com.mobilise.BookManagementSystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookInfoValidations {
    private final BookRepository bookRepository;
    // Check if Title field is empty or null
    public void requiredTitleField(String title) throws ValidTitleException {
        if (title == null || title.isBlank() || title.isEmpty()) {
            log.error("Title field is required! and cannot be empty/blank");
            throw new ValidTitleException("Title field is required! and cannot be empty/blank");
        }
    }

    // Check if Author field is empty or null
    public void requiredAuthorField(String author) throws ValidAuthorException {
        if (author == null || author.isBlank() || author.isEmpty()) {
            log.error("Author field is required! and cannot be empty/blank");
            throw new ValidAuthorException("Author field is required! and cannot be empty/blank");
        }
    }


    // Check if the bookTitle already exists in the database
    public void isBookTitleAlreadyExists(String bookTitle) {
        // Using repository method to find an existing book by unique book title
        Optional<BookLibrary> existingBookTitle = bookRepository.findBookByTitleIgnoreCase(bookTitle);
        if (existingBookTitle.isPresent()) {
            log.error("Book with this title: " + bookTitle + " already exists. Please use a different title");
            throw new AlreadyExistsException("Book with this title: " + bookTitle + " already exists. Please use a different title");
        }
    }

    // Title validation method
    public void validateTitle(String title) throws ValidTitleException {
        if (title == null || title.isBlank() || title.length() < 2 || title.length() > 50 || !title.matches("^[a-zA-Z0-9 ]+$")) {
            log.error("Title: " + title + " must be between 2 and 50 characters and contain only English alphabet or a combination of English alphabet and number");
            throw new ValidTitleException("Title must be between 2 and 50 characters and contain only English alphabet or a combination of English alphabet and number");
        }
    }

    // Author validation method
    public void validateAuthor(String author) {
        if (author == null || author.isBlank() || author.length() < 2 || author.length() > 50) {
            log.error("Author Name(s): " + author + " must be between 2 and 50 characters");
            throw new ValidAuthorException("Author Name(s) must be between 2 and 50 characters");
        }
    }

    // Publication year validation method
    public void validatePublicationYear(Year publicationYear) {
        if (publicationYear == null || publicationYear.isBefore(Year.of(2001)) || publicationYear.isAfter(Year.now())) {
            log.error("Publication year: " + publicationYear + " must be in the range between 2001 and the present year");
            throw new ValidPublicationYearException("Publication year must be in the range between 2001 and the present year");
        }
    }

}
