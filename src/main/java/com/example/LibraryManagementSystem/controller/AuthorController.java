package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.AuthorDTO;
import com.example.LibraryManagementSystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // USER endpoints
    @GetMapping("/user/authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthorsForAdmin() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/user/authors/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    // ADMIN endpoints
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/admin/authors/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id,
                                                  @Valid @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
    }

    @DeleteMapping("/admin/authors/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().body("Author deleted successfully");
    }
}