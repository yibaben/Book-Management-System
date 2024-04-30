package com.mobilise.BookManagementSystem.service;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.repository.BookRepository;
import com.mobilise.BookManagementSystem.validator.BookInfoValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookServices {
    private final BookRepository bookRepository;
    private final BookInfoValidations bookInfoValidations;
    private final ModelMapper modelMapper;

    @Override
    public BookResponse addNewBook(BookRequest bookRequest) {
        return null;
    }
}
