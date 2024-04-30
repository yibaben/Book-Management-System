package com.mobilise.BookManagementSystem.repository;

import com.mobilise.BookManagementSystem.entity.BookLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookLibrary, Long> {

    Optional<BookLibrary> findBookByTitleIgnoreCase(String bookTitle);
}
