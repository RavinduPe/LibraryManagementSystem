package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.entity.Borrow;
import com.example.LibraryManagementSystem.entity.User;
import com.example.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUser(User user);
    List<Borrow> findByReturnedFalse();
    List<Borrow> findByUserAndReturnedFalse(User user);
    Optional<Borrow> findByUserAndBookAndReturnedFalse(User user, Book book);
    boolean existsByUserAndBookAndReturnedFalse(User user, Book book);
    boolean existsByUserAndReturnedFalse(User user);
    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.returned = false")
    Long countActiveBorrows();
}