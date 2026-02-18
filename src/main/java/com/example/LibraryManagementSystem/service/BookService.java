package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.BookDto;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Book;
import com.example.LibraryManagementSystem.entity.Member;
import com.example.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberRepository memberRepository;

    public BookDto saveBook(BookDto bookDto) {

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setPrice(bookDto.getPrice());
        book.setAvailable(bookDto.isAvailable());

        Author author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);

        BookDto response = new BookDto();
        response.setTitle(savedBook.getTitle());
        response.setGenre(savedBook.getGenre());
        response.setPrice(savedBook.getPrice());
        response.setAvailable(savedBook.isAvailable());
        response.setAuthorId(author.getId());

        return response;
    }


    public List<BookDto> getAllBooks(){
        List<Book> booklist = bookRepository.findAll();
        return modelMapper.map(booklist,new TypeToken<List<BookDto>>(){}.getType());
    }


    public BookDto getBookById(String bookId){
        long id = Long.parseLong(bookId);
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));
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

    public void borrowBook(Long memberId,Long bookId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("Member not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));
        ;

        if (book.isAvailable()){
            throw new RuntimeException("Book is not available");
        }

        member.getBorrowedBooks().add(book);
        book.setAvailable(false);

        memberRepository.save(member);
        bookRepository.save(book);
    }

    public void returnBook(Long memberId,Long bookId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("Member not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));


        if(!member.getBorrowedBooks().contains(book)){
            throw new RuntimeException("Book not borrowed by this member");
        }

        member.getBorrowedBooks().remove(book);
        book.setAvailable(true);

        memberRepository.save(member);
        bookRepository.save(book);
    }
}
