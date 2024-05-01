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
     * Checks if a book exists by the given publication year.
     * It declares a boolean method called existsByPublicationYear that takes a parameter of type Year named publicationYear.
     * The purpose of this method is to check if a book with the given publication year already exists in the repository.
     * It returns true if a book with the given publication year exists, and false otherwise.
     * @param  publicationYear  the publication year to check for
     * @return                  true if a book exists with the given publication year, false otherwise
     */
    boolean existsByPublicationYear(Year publicationYear);

    /**
     * This Query defines a method that searches for books in a library based on the input parameters
     * such as title, author, ISBN, and publication year.
     * The method uses a SQL-like query to filter books by matching the input parameters with the
     * corresponding fields in the database.
     * @param  title    parameter used to search for books by title
     * @param  author  parameter used to search for books by author
     * @param  isbn    parameter used to search for books by ISBN
     * @param  publicationYear  parameter used to search for books by publication year
     * @return  a list of books that match the search criteria
     */
    @Query("SELECT ic FROM BookLibrary ic WHERE LOWER(ic.title) LIKE CONCAT('%', COALESCE(LOWER(:title), ''), '%') " +
            "OR LOWER(ic.author) LIKE CONCAT('%', COALESCE(LOWER(:author), ''), '%') " +
            "OR LOWER(ic.isbn) LIKE CONCAT('%', COALESCE(LOWER(:isbn), ''), '%') " +
            "OR ic.publicationYear = :publicationYear")
    List<BookLibrary> searchByTitleOrAuthorOrIsbnOrPublicationYear(@Param("title") String title,
                                                          @Param("author") String author,
                                                          @Param("isbn") String isbn,
                                                          @Param("publicationYear") Year publicationYear);

}
