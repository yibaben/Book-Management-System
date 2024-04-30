package com.mobilise.BookManagementSystem.exception;

public class AuthorRequiredException extends RuntimeException{
    public AuthorRequiredException(String message){
        super(message);
    }
}
