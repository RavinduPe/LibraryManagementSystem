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
        BookDto response = bookService.saveBook(bookDto);

        return ResponseEntity.ok(response);
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

    @PostMapping("/borrow")
    public ResponseEntity<String> barrowBook(@RequestParam Long memberId , @RequestParam Long bookId){
        bookService.borrowBook(memberId,bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long memberId, @RequestParam Long bookId){
        bookService.returnBook(memberId,bookId);
        return ResponseEntity.ok("Book returned successfully");
    }
}
