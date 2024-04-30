package com.mobilise.BookManagementSystem.controller;

import com.mobilise.BookManagementSystem.service.BookServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Mobilise Book Management System - BookLibrary Controller",
        description = "Exposes REST APIs for Book Library Controller"
)

@RestController
@RequestMapping("/book")
@Slf4j
@RequiredArgsConstructor
public class BookLibraryController {
    private final BookServices bookServices;
}
