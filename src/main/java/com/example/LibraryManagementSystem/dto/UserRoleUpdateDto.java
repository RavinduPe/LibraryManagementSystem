package com.example.LibraryManagementSystem.dto;

import com.example.LibraryManagementSystem.enums.Role;

public class UserRoleUpdateDto {
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}