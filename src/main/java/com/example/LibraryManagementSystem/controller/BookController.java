package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping(value = "api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity< BookDto > saveBook(@RequestBody BookDto bookDto)
    {
        BookDto responce = bookService.saveBook(bookDto);

        return ResponseEntity.ok(responce);
    }

    @GetMapping()
    public ResponseEntity<List<BookDto>> getAllBook(){
        List<BookDto> books = bookService.getAllBooks();

        return ResponseEntity.ok(books);
    }
    @GetMapping("{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable String bookId){
        BookDto book = bookService.getBookById(bookId);
        return ResponseEntity.ok(book);
    }
    @DeleteMapping("{bookId}")
    public ResponseEntity<String> deleteBookById(@PathVariable String bookId)
    {
        String confirmResponse = bookService.deleteById(bookId);
        return ResponseEntity.ok(confirmResponse);
    }

    @PutMapping("{bookId}")
    public  ResponseEntity<String> updateBookById(@PathVariable String bookId , @RequestBody Map<String, String> requestBody)
    {
        String newTitle = requestBody.get("title");
        String updateResponse = bookService.updateBookTitle(bookId, newTitle);

        return ResponseEntity.ok(updateResponse);
    }
}
