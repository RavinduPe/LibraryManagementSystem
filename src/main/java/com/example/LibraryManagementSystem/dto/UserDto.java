package com.example.LibraryManagementSystem.dto;

import com.example.LibraryManagementSystem.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("borrowedBooksCount")
    private int borrowedBooksCount;

    // Default constructor (required for Jackson)
    public UserDto() {}

    // Parameterized constructor
    public UserDto(Long id, String username, Role role, int borrowedBooksCount) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.borrowedBooksCount = borrowedBooksCount;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBorrowedBooksCount(int borrowedBooksCount) {
        this.borrowedBooksCount = borrowedBooksCount;
    }

    // Optional: toString method for debugging
    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", borrowedBooksCount=" + borrowedBooksCount +
                '}';
    }
}