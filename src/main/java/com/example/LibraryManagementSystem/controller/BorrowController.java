package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BorrowDTO;
import com.example.LibraryManagementSystem.dto.BorrowRequestDTO;
import com.example.LibraryManagementSystem.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // USER endpoints
    @PostMapping("/user/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody BorrowRequestDTO borrowRequest) {
        try {
            BorrowDTO borrow = borrowService.borrowBook(borrowRequest);
            return ResponseEntity.ok(borrow);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/return/{borrowId}")
    public ResponseEntity<?> adminReturnBook(@PathVariable Long borrowId) {
        try {
            BorrowDTO borrow = borrowService.returnBook(borrowId);
            return ResponseEntity.ok(borrow);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/return/{borrowId}")
    public ResponseEntity<?> userReturnBook(@PathVariable Long borrowId) {
        try {
            BorrowDTO borrow = borrowService.returnBook(borrowId);
            return ResponseEntity.ok(borrow);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/borrows")
    public ResponseEntity<List<BorrowDTO>> getMyBorrows() {
        return ResponseEntity.ok(borrowService.getCurrentUserBorrows());
    }

    // ADMIN endpoints
    @GetMapping("/admin/borrows")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<BorrowDTO>> getAllBorrows() {
        try {
            List<BorrowDTO> borrows = borrowService.getAllBorrows();
            return ResponseEntity.ok(borrows);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/borrows/active")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<BorrowDTO>> getActiveBorrows() {
        try {
            List<BorrowDTO> activeBorrows = borrowService.getActiveBorrows();
            return ResponseEntity.ok(activeBorrows);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}