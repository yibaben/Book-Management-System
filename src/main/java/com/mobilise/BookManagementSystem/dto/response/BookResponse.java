package com.mobilise.BookManagementSystem.dto.response;

import lombok.*;

import java.time.Year;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;
    private Year publicationYear;
}
