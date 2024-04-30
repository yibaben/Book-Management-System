package com.mobilise.BookManagementSystem.exception;

public class TitleRequiredException extends RuntimeException{
    public TitleRequiredException(String message){
        super(message);
    }
}
