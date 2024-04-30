package com.mobilise.BookManagementSystem.validator;

import com.mobilise.BookManagementSystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookInfoValidations {
    private final BookRepository bookRepository;

}
