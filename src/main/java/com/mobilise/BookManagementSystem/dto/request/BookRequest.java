package com.mobilise.BookManagementSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookRequest {
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;
    private Year publicationYear;
}
