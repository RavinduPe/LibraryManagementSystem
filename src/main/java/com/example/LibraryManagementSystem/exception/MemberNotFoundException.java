package com.example.LibraryManagementSystem.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message){
        super(message);
    }
}
