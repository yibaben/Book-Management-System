package com.mobilise.BookManagementSystem.repository;

import com.mobilise.BookManagementSystem.entity.BookLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookLibrary, Long> {

}
