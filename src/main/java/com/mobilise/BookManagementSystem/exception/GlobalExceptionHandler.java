package com.mobilise.BookManagementSystem.exception;

import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mobilise.BookManagementSystem.util.ApiResponseUtils.buildErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handling validation exceptions for method arguments
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        // Collecting field errors
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.merge(error.getField(), Objects.requireNonNull(error.getDefaultMessage()), (existing, additional) -> String.format("%s, %s", existing, additional));
        });

        // Building and returning error response
        return new ResponseEntity<>(new ApiResponse("Error Occurred While Saving Book", HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors),
                HttpStatus.BAD_REQUEST);
    }

    // Handling custom BookCreationException
    @ExceptionHandler(BookCreationException.class)
    public ResponseEntity<ApiResponse> handleBookCreationException(BookCreationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    // Handling custom NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

}
