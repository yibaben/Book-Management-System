package com.mobilise.BookManagementSystem.controller;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.dto.response.PaginatedBookResponse;
import com.mobilise.BookManagementSystem.exception.*;
import com.mobilise.BookManagementSystem.service.BookServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.mobilise.BookManagementSystem.util.ApiResponseUtils.buildErrorResponse;
import static com.mobilise.BookManagementSystem.util.ApiResponseUtils.buildSuccessResponse;

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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewBook(@RequestBody BookRequest bookRequest) {
            BookResponse response = bookServices.addNewBook(bookRequest);
            return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse(response, HttpStatus.OK));
        }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllBooksWithPagination(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
            PaginatedBookResponse response = bookServices.getAllBooksWithPagination(pageNo, pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse(response, HttpStatus.OK));
        }
    }
