package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BorrowRequestDTO;
import com.example.LibraryManagementSystem.entity.Borrow;
import com.example.LibraryManagementSystem.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
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
            Borrow borrow = borrowService.borrowBook(borrowRequest);
            return ResponseEntity.ok(borrow);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/return/{borrowId}")
    public ResponseEntity<?> returnBook(@PathVariable Long borrowId) {
        try {
            Borrow borrow = borrowService.returnBook(borrowId);
            return ResponseEntity.ok(borrow);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/borrows")
    public ResponseEntity<List<Borrow>> getMyBorrows() {
        return ResponseEntity.ok(borrowService.getCurrentUserBorrows());
    }

    // ADMIN endpoints
    @GetMapping("/admin/borrows")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

    @GetMapping("/admin/borrows/active")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Borrow>> getActiveBorrows() {
        return ResponseEntity.ok(borrowService.getActiveBorrows());
    }
}