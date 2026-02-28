package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BorrowRequestDTO;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Borrow;
import com.example.LibraryManagementSystem.entity.User;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.BorrowRepository;
import com.example.LibraryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Borrow borrowBook(BorrowRequestDTO borrowRequest) {
        User currentUser = userService.getCurrentUser();
        Book book = bookRepository.findById(borrowRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + borrowRequest.getBookId()));

        // Check if book is available
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        // Check if user already has this book borrowed and not returned
        if (borrowRepository.existsByUserAndBookAndReturnedFalse(currentUser, book)) {
            throw new RuntimeException("You have already borrowed this book and haven't returned it yet");
        }

        // Create borrow record
        Borrow borrow = new Borrow();
        borrow.setUser(currentUser);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturned(false);

        // Update book availability
        book.setAvailable(false);
        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }

    @Transactional
    public Borrow returnBook(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + borrowId));

        // Check if already returned
        if (borrow.isReturned()) {
            throw new RuntimeException("Book has already been returned");
        }

        // Update borrow record
        borrow.setReturned(true);
        borrow.setReturnDate(LocalDate.now());

        // Update book availability
        Book book = borrow.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }

    public List<Borrow> getCurrentUserBorrows() {
        User currentUser = userService.getCurrentUser();
        return borrowRepository.findByUser(currentUser);
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public List<Borrow> getActiveBorrows() {
        return borrowRepository.findByReturnedFalse();
    }
}