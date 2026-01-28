package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.title = :title WHERE b.id = :id")
    int updateAuthorByName(@Param("id") Long id, @Param("title") String title);
}
