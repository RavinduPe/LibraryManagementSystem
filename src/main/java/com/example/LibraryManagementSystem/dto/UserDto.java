package com.example.LibraryManagementSystem.dto;

import com.example.LibraryManagementSystem.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("borrowedBooksCount")
    private int borrowedBooksCount;

    @JsonProperty("joinedDate")
    private LocalDateTime joinedDate;
}