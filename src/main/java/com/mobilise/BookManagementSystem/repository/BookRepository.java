package com.mobilise.BookManagementSystem.repository;

import com.mobilise.BookManagementSystem.entity.BookLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookLibrary, Long> {

    /**
     * Find a book in the library by title ignoring case.
     * This query defines a method named findBookByTitleIgnoreCase that takes a String parameter bookTitle and
     * returns an Optional of type BookLibrary. The method is likely used to find a book in a library by its title,
     * ignoring case sensitivity. The use of Optional indicates that the book may or may not be found,
     * preventing null pointer exceptions.
     * @param  bookTitle  the title of the book to search for
     * @return            an Optional containing the BookLibrary object if found, empty otherwise
     */
    Optional<BookLibrary> findBookByTitleIgnoreCase(String bookTitle);

    /**
     * Find all books in the library by publication year.
     * This is a query that finds all books in a library based on their publication year.
     * The method takes the publication year as input and returns a list of BookLibrary objects
     * that match the provided publication year.
     * @param  publicationYear   the year of publication to search for
     * @return                   a list of BookLibrary objects matching the publication year
     */
    List<BookLibrary> findAllBooksByPublicationYear(Year publicationYear);


    /**
     * This Query defines a method that searches for books in a library based on the input parameters
     * such as title, author, and ISBN.
     * The method uses a SQL-like query to filter books by matching the input parameters with the
     * corresponding fields in the database.
     * @param  title    parameter used to search for books by title
     * @param  author  parameter used to search for books by author
     * @param  isbn    parameter used to search for books by ISBN
     * @return  a list of books that match the search criteria
     */
    @Query("SELECT ic FROM BookLibrary ic WHERE LOWER(ic.title) LIKE CONCAT('%', COALESCE(LOWER(:title), ''), '%') " +
            "OR LOWER(ic.author) LIKE CONCAT('%', COALESCE(LOWER(:author), ''), '%') " +
            "OR LOWER(ic.isbn) LIKE CONCAT('%', COALESCE(LOWER(:isbn), ''), '%')")
    List<BookLibrary> searchByTitleOrAuthorOrIsbn(@Param("title") String title, @Param("author") String author, @Param("isbn") String isbn);

}
