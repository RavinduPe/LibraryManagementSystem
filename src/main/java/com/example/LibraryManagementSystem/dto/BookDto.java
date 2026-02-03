package com.example.LibraryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Genre is required")
    private String genre;
    @Positive(message = "Price must be positive")
    private double price;
    private boolean available;
    @NotNull(message = "Author id is required")
    private Long authorId;
    private String authorName;
}
