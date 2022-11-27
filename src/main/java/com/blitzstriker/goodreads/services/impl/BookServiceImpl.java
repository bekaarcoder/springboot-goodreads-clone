package com.blitzstriker.goodreads.services.impl;

import com.blitzstriker.goodreads.entity.Author;
import com.blitzstriker.goodreads.entity.Book;
import com.blitzstriker.goodreads.exceptions.ResourceNotFoundException;
import com.blitzstriker.goodreads.payload.book.BookDto;
import com.blitzstriker.goodreads.payload.book.BookResponse;
import com.blitzstriker.goodreads.repositories.AuthorRepository;
import com.blitzstriker.goodreads.repositories.BookRepository;
import com.blitzstriker.goodreads.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookResponse addBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        findAndUpdateAuthor(bookDto, book);
        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookResponse.class);
    }

    @Override
    public BookResponse updateBook(BookDto bookDto, Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        book.setName(bookDto.getName() != null ? bookDto.getName() : book.getName());
        book.setPublishedDate(bookDto.getPublishedDate() != null ? bookDto.getPublishedDate() : book.getPublishedDate());
        book.setCoverImage(bookDto.getCoverImage() != null ? bookDto.getCoverImage() : book.getCoverImage());
        book.setDescription(bookDto.getDescription() != null ? bookDto.getDescription() : book.getDescription());
        book.setPages(bookDto.getPages() != null ? bookDto.getPages() : book.getPages());
        findAndUpdateAuthor(bookDto, book);
        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookResponse.class);
    }

    @Override
    public BookResponse getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        book.removeAuthors(book.getAuthors());
        bookRepository.delete(book);
    }

    private Book findAndUpdateAuthor(BookDto bookDto, Book book) {
        Author existingAuthor = authorRepository.findByFirstNameAndLastName(bookDto.getAuthorFirstName(), bookDto.getAuthorLastName());
        if(existingAuthor != null) {
            book.getAuthors().add(existingAuthor);
        } else {
            Author author = new Author();
            author.setFirstName(bookDto.getAuthorFirstName());
            author.setLastName(bookDto.getAuthorLastName());
            book.getAuthors().add(author);
        }
        return book;
    }
}
