package com.mobilise.BookManagementSystem.util;

import com.mobilise.BookManagementSystem.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class ApiResponseUtils {
    public static ApiResponse buildApiResponse(Object d, HttpStatus status){
        return ApiResponse.builder()
                .message("successful")
                .data(d)
                .status(status)
                .build();
    }

    public static ApiResponse buildErrorResponse(Object d, HttpStatus status) {
        return ApiResponse.builder()
                .message("error")
                .status(status)
                .data(d)
                .build();
    }
}
