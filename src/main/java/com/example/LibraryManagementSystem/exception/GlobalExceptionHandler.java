package com.example.LibraryManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String , String>> handleBookNotFound(BookNotFoundException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleMemberNotFound(MemberNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<Map<String, String>> handleBookNotAvailable(BookNotAvailableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
