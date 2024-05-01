package com.mobilise.BookManagementSystem.validator;

import com.mobilise.BookManagementSystem.entity.BookLibrary;
import com.mobilise.BookManagementSystem.exception.*;
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


    // This method checks if the title field is null, empty, or blank, and if so,
    // it logs an error and throws a ValidTitleException with a message stating
    // that the title field is required and cannot be empty or blank.
    public void requiredTitleField(String title) throws ValidTitleException {
        if (title == null || title.isBlank() || title.isEmpty()) {
            log.error("Title field is required! and cannot be empty/blank");
            throw new TitleRequiredException("Title field is required! and cannot be empty/blank");
        }
    }

    // his method checks if the author field is null, empty, or blank.
    // If it is, it logs an error and throws a ValidAuthorException.
    public void requiredAuthorField(String author) throws ValidAuthorException {
        if (author == null || author.isBlank() || author.isEmpty()) {
            log.error("Author field is required! and cannot be empty/blank");
            throw new AuthorRequiredException("Author field is required! and cannot be empty/blank");
        }
    }


    // This Java code snippet checks if a book title already exists in the database
    // by querying the bookRepository using the provided bookTitle.
    // If a book with the same title already exists, it logs an error and throws an AlreadyExistsException.
    public void isBookTitleAlreadyExists(String bookTitle) {
        // Using repository method to find an existing book by unique book title
        Optional<BookLibrary> existingBookTitle = bookRepository.findBookByTitleIgnoreCase(bookTitle);
        if (existingBookTitle.isPresent()) {
            log.error("Book with this title: " + bookTitle + " already exists. Please use a different title");
            throw new AlreadyExistsException("Book with this title: " + bookTitle + " already exists. Please use a different title");
        }
    }

    // This implementation defines a method validateTitle that checks if the input title meets certain criteria.
    // It ensures that the title is not null, not empty or blank, has a length between 2 and 50 characters,
    // and contains only English alphabets or a combination of English alphabets and numbers.
    // If the title does not meet these criteria, it logs an error and throws a ValidTitleException with an appropriate message.
    public void validateTitle(String title) throws ValidTitleException {
        if (title == null || title.isBlank() || title.length() < 2 || title.length() > 50 || !title.matches("^[a-zA-Z0-9 ]+$")) {
            log.error("Title: " + title + " must be between 2 and 50 characters and contain only English alphabet or a combination of English alphabet and number");
            throw new ValidTitleException("Title must be between 2 and 50 characters and contain only English alphabet or a combination of English alphabet and number");
        }
    }


    // This implementation defines a method called validateAuthor that takes a String parameter called author.
    // It checks if the author is null, blank, less than 2 characters, or more than 50 characters.
    // If any of these conditions are true, it logs an error message and throws a ValidAuthorException with a specific error message.
    public void validateAuthor(String author) {
        if (author == null || author.isBlank() || author.length() < 2 || author.length() > 50) {
            log.error("Author Name(s): " + author + " must be between 2 and 50 characters");
            throw new ValidAuthorException("Author Name(s) must be between 2 and 50 characters");
        }
    }

    // This implementation defines a method to validate if a given publication year is within a specified range,
    // between 2001 and the current year. If the year is null, before 2001, or after the current year,
    // it logs an error and throws a custom exception indicating the publication year is invalid.
    public void validatePublicationYear(Year publicationYear) {
        if (publicationYear == null || publicationYear.isBefore(Year.of(2001)) || publicationYear.isAfter(Year.now())) {
            log.error("Publication year: " + publicationYear + " must be in the range between 2001 and the present year");
            throw new ValidPublicationYearException("Publication year must be in the range between 2001 and the present year");
        }
    }

}
