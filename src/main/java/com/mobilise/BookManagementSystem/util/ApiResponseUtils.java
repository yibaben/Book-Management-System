package com.mobilise.BookManagementSystem.util;

import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponseUtils {
    /**
     * This is ia a util method that builds and returns a success response for an API.
     * It takes in data, a status code, and constructs an ApiResponse object with a success message,
     * the provided data, and the given HTTP status.
     * @param  d      The data to include in the response
     * @param  status The HTTP status code of the response
     * @return        The ApiResponse containing the success message, data, and status
     */
    public static ApiResponse buildSuccessResponse(Object d, HttpStatus status){
        return ApiResponse.builder()
                .message("successful")
                .data(d)
                .status(status)
                .dateTime(LocalDateTime.now())
                .build();
    }

    /**
     * A utility method that builds and returns an error response in the form of an ApiResponse object.
     * It sets the message, status, and data for the error response before building and returning it.
     *
     * @param  d     the data to include in the response
     * @param  status the HTTP status of the response
     * @return       the error response object
     */
    public static ApiResponse buildErrorResponse(Object d, HttpStatus status) {
        return ApiResponse.builder()
                .message("Error Occurred:")
                .status(status)
                .data(d)
                .dateTime(LocalDateTime.now())
                .build();
    }
}
