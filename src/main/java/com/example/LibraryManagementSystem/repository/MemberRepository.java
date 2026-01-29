package com.example.LibraryManagementSystem.repository;


import com.example.LibraryManagementSystem.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Member b SET b.name = :name WHERE b.id = :id")
    int updateMemberByName(@Param("id") Long id, @Param("name") String name);
}
