package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookDTO;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public BookDTO createBook(BookDTO dto) {

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setGenre(dto.getGenre());
        book.setPrice(dto.getPrice());
        book.setAuthor(author);

        // ⭐ Copy system
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getTotalCopies());

        Book saved = bookRepository.save(book);

        return convertToDTO(saved);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getAvailableBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return convertToDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO dto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (dto.getTitle() != null)
            book.setTitle(dto.getTitle());

        if (dto.getGenre() != null)
            book.setGenre(dto.getGenre());

        if (dto.getPrice() != null)
            book.setPrice(dto.getPrice());

        if (dto.getAuthorId() != null) {
            Author author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));

            book.setAuthor(author);
        }

        if (dto.getTotalCopies() != null) {
            int diff = dto.getTotalCopies() - book.getTotalCopies();
            book.setTotalCopies(dto.getTotalCopies());
            book.setAvailableCopies(book.getAvailableCopies() + diff);
        }

        return convertToDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
    }

    private BookDTO convertToDTO(Book book) {

        BookDTO dto = new BookDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setGenre(book.getGenre());
        dto.setPrice(book.getPrice());

        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorName(book.getAuthor().getName());
        }

        return dto;
    }
}