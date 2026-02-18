package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.AuthorDto;
import com.example.LibraryManagementSystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@ResponseBody
@RequestMapping(value = "api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping()
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto)
    {
        AuthorDto response = authorService.createUser(authorDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<AuthorDto>> getAllAuthors()
    {
        List<AuthorDto> authors = authorService.getAllAuthors();

        return ResponseEntity.ok(authors);
    }

    @GetMapping("{authorId}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable String authorId)
    {
        AuthorDto authors = authorService.getAuthorById(Long.valueOf(authorId));

        return ResponseEntity.ok(authors);
    }

    @DeleteMapping("{authorId}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable String authorId)
    {
        String confirmResponse = authorService.authorDeleteById(authorId);
        return ResponseEntity.ok(confirmResponse);
    }

    @PutMapping("{authorId}")
    public  ResponseEntity<String> updateAuthorById(@PathVariable String authorId , @RequestBody Map<String, String> requestBody)
    {
        String newName = requestBody.get("name");
        String updateResponse = authorService.updateAuthorName(authorId, newName);

        return ResponseEntity.ok(updateResponse);
    }



}
