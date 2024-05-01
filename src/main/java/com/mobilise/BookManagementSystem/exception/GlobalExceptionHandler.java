package com.mobilise.BookManagementSystem.exception;

import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.mobilise.BookManagementSystem.util.ApiResponseUtils.buildErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handling global BookCreationException
    // This is using an exception handler to catch BookCreationException and
    // return a response with a status of Bad Request along with an error message.
    @ExceptionHandler(BookCreationException.class)
    public ResponseEntity<ApiResponse> handleBookCreationException(BookCreationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    // Handling global NotFoundException
    // This handles a global exception called NotFoundException.
    // When this exception is thrown, the method handleNotFoundException is called,
    // it returns a ResponseEntity object with a HttpStatus of NOT_FOUND and an
    // ApiResponse body that contains an error message and the same HttpStatus.
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    // Handling global AlreadyExistsException
    // This specifically handles the AlreadyExistsException by
    // returning a BAD_REQUEST response along with an error message.
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExistsException(AlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    // Handling global ValidTitleException
    // This specifically handles the ValidTitleException by
    // returning a response entity with a Bad Request status and an error message extracted from the exception.
    @ExceptionHandler(ValidTitleException.class)
    public ResponseEntity<ApiResponse> handleValidTitleException(ValidTitleException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Handling global ValidAuthorException
    // This also specifically handles an exception to type ValidAuthorException by
    // returning a specific HTTP response with a custom error message.
    @ExceptionHandler(ValidAuthorException.class)
    public ResponseEntity<ApiResponse> handleValidAuthorException(ValidAuthorException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Handling global ValidPublicationYearException
    // this deals with ValidPublicationYearException.
    // It returns a ResponseEntity with a BAD_REQUEST status and
    // an error response message based on the exception message.
    @ExceptionHandler(ValidPublicationYearException.class)
    public ResponseEntity<ApiResponse> handleValidPublicationYearException(ValidPublicationYearException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Handling global TitleRequiredException
    // A Spring framework controller method that handles an exception called TitleRequiredException.
    // When this specific exception is thrown, it returns a response entity with
    // a status of 400 (Bad Request) and includes an error message in the response body.
    @ExceptionHandler(TitleRequiredException.class)
    public ResponseEntity<ApiResponse> handleTitleRequiredException(TitleRequiredException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    //Handling global AuthorRequiredException
    // This method handles the AuthorRequiredException exception.
    // When this exception is thrown, the method handleAuthorRequiredException is called.
    // It returns a ResponseEntity object with a HttpStatus of BAD_REQUEST and a body that
    // contains an error response message and the same HttpStatus.
    @ExceptionHandler(AuthorRequiredException.class)
    public ResponseEntity<ApiResponse> handleAuthorRequiredException(AuthorRequiredException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

}
