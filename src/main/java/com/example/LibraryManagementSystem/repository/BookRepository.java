package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.title = :title WHERE b.id = :id")
    int updateBookByTitleBook(@Param("id") Long id, @Param("title") String title);
    boolean existsByAuthorId(Long authorId);


}
