package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AuthorDto;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthorDto createUser(AuthorDto authorDto)
    {
        authorRepository.save(modelMapper.map(authorDto, Author.class));
        return authorDto;
    }

    public List<AuthorDto> getAllAuthors(){
        List<Author> author = authorRepository.findAll();
        return modelMapper.map(author,new TypeToken<List<AuthorDto>>(){}.getType());
    }

    public void authorDeleteById(Long id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        boolean hasBooks = bookRepository.existsByAuthorId(id);

        if (hasBooks) {
            throw new IllegalStateException("Cannot delete author with existing books");
        }

        authorRepository.delete(author);
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + id));
        return modelMapper.map(author, AuthorDto.class);
    }

//    public String authorDeleteById(String authorId) {
//        long id = Long.parseLong(authorId);
//        authorRepository.deleteById(id);
//        return "Deleted successfully Where Book ID : " + authorId ;
//    }

    public String updateAuthorName(String authorId, String authorName) {
        Long id = Long.parseLong(authorId);
        int updatedRows = authorRepository.updateAuthorByName(id, authorName);

        if (updatedRows == 0) {
            return "No book found with ID: " + authorId;
        }
        return "Updated book " + authorId + " with new title: " + authorName;
    }

}
