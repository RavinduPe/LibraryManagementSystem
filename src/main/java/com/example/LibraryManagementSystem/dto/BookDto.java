package com.example.LibraryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String title;
    private String genre;
    private double price;
    private boolean available;

    private Long authorId;
    private String authorName;
}
