package com.mobilise.BookManagementSystem.controller;

import com.mobilise.BookManagementSystem.dto.request.BookRequest;
import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import com.mobilise.BookManagementSystem.dto.response.BookResponse;
import com.mobilise.BookManagementSystem.dto.response.PaginatedBookResponse;
import com.mobilise.BookManagementSystem.service.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(
            summary = "Add New Book REST API",
            description = "This REST API is used to Add a New Book to a Database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
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
        @GetMapping("/get/by/id/{id}")
        public ResponseEntity<ApiResponse> getBookById(@PathVariable Long id) {
            BookResponse response = bookServices.getBookById(id);
            return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse(response, HttpStatus.OK));
        }

    @GetMapping("/search/by/searchText/{searchText}")
    public ResponseEntity<ApiResponse> getContributorBySearchParam(@PathVariable String searchText) {
            List<BookResponse> response = bookServices.searchBookByTitleOrAuthorOrIsbnOrPublisherYear(searchText);
            return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse(response, HttpStatus.OK));
        }

    @PutMapping("/update/by/id/{id}")
    public ResponseEntity<ApiResponse> updateBookDetails(@PathVariable Long id, @Valid @RequestBody BookRequest updateRequest) {
            BookResponse response = bookServices.updateBook(id, updateRequest);
            return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse(response, HttpStatus.OK));
        }

    @DeleteMapping("/delete/by/id/{id}")
    public ResponseEntity<ApiResponse> deleteBookById(@PathVariable Long id) {
        ApiResponse response = bookServices.deleteBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(buildSuccessResponse(response, HttpStatus.OK));
        }
    }
