package com.example.LibraryManagementSystem.dto;

public class AuthorDTO {
    private Long id;
    private String name;
    private String bio;

    // Constructors
    public AuthorDTO() {}

    public AuthorDTO(Long id, String name, String bio) {
        this.id = id;
        this.name = name;
        this.bio = bio;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}