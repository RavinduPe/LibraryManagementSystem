package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BorrowDTO;
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
import java.util.stream.Collectors;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public BorrowDTO borrowBook(BorrowRequestDTO request) {

        User currentUser = userService.getCurrentUser();

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        //  Check copy availability
        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        //  Prevent duplicate borrow
        if (borrowRepository.existsByUserAndBookAndReturnedFalse(currentUser, book)) {
            throw new RuntimeException("You already borrowed this book");
        }

        Borrow borrow = new Borrow();
        borrow.setUser(currentUser);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturned(false);

        //  Reduce available copy
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Borrow savedBorrow = borrowRepository.save(borrow);

        return convertToDTO(savedBorrow);
    }

    @Transactional
    public BorrowDTO returnBook(Long borrowId) {

        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (borrow.isReturned()) {
            throw new RuntimeException("Book already returned");
        }

        borrow.setReturned(true);
        borrow.setReturnDate(LocalDate.now());

        // Increase available copy
        Book book = borrow.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        Borrow updatedBorrow = borrowRepository.save(borrow);

        return convertToDTO(updatedBorrow);
    }

    public List<BorrowDTO> getCurrentUserBorrows() {

        User currentUser = userService.getCurrentUser();

        return borrowRepository.findByUser(currentUser).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BorrowDTO> getAllBorrows() {

        return borrowRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BorrowDTO> getActiveBorrows() {
        
        return borrowRepository.findByReturnedFalse().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BorrowDTO convertToDTO(Borrow borrow) {

        BorrowDTO dto = new BorrowDTO();

        dto.setId(borrow.getId());
        dto.setBorrowDate(borrow.getBorrowDate());
        dto.setReturnDate(borrow.getReturnDate());
        dto.setReturned(borrow.isReturned());

        if (borrow.getUser() != null) {
            dto.setUserId(borrow.getUser().getId());
            dto.setUsername(borrow.getUser().getUsername());
        }

        if (borrow.getBook() != null) {
            dto.setBookId(borrow.getBook().getId());
            dto.setBookTitle(borrow.getBook().getTitle());

            if (borrow.getBook().getAuthor() != null) {
                dto.setAuthorName(borrow.getBook().getAuthor().getName());
            }
        }

        return dto;
    }
}