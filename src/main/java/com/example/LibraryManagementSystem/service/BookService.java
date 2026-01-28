package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public  BookDto saveBook(BookDto bookDto){
        bookRepository.save(modelMapper.map(bookDto, Book.class));
        return bookDto;
    }

    public List<BookDto> getAllBooks(){
        List<Book> booklist = bookRepository.findAll();
        return modelMapper.map(booklist,new TypeToken<List<BookDto>>(){}.getType());
    }

    public BookDto getBookById(String bookId){
        long id = Long.parseLong(bookId);
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));
        Class<? extends BookDto> BookDto;
        return modelMapper.map(book,BookDto.class);
    }

    public String deleteById(String bookId) {
        long id = Long.parseLong(bookId);
        bookRepository.deleteById(id);
        return "Deleted successfully Where Book ID : " + bookId ;
    }
    public String updateBookTitle(String bookId, String newTitle) {
        Long id = Long.parseLong(bookId);
        int updatedRows = bookRepository.updateBookByTitleBook(id, newTitle);

        if (updatedRows == 0) {
            return "No book found with ID: " + bookId;
        }
        return "Updated book " + bookId + " with new title: " + newTitle;
    }

}
