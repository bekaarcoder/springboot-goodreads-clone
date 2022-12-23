package com.blitzstriker.goodreads.services.impl;

import com.blitzstriker.goodreads.entity.Author;
import com.blitzstriker.goodreads.entity.Book;
import com.blitzstriker.goodreads.exceptions.ResourceNotFoundException;
import com.blitzstriker.goodreads.payload.book.BookDto;
import com.blitzstriker.goodreads.payload.book.BookResponse;
import com.blitzstriker.goodreads.payload.book.BooksResponse;
import com.blitzstriker.goodreads.repositories.AuthorRepository;
import com.blitzstriker.goodreads.repositories.BookRepository;
import com.blitzstriker.goodreads.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    @Override
    public BooksResponse getAllBooks(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPages = bookRepository.findAll(pageable);

        List<Book> books = bookPages.getContent();
        List<BookResponse> content = books.stream().map(book -> modelMapper.map(book, BookResponse.class)).toList();
        BooksResponse booksResponse = new BooksResponse();
        booksResponse.setBooks(content);
        booksResponse.setPageNumber(bookPages.getNumber());
        booksResponse.setPageSize(bookPages.getSize());
        booksResponse.setTotalPages(bookPages.getTotalPages());
        booksResponse.setTotalElements(bookPages.getNumberOfElements());
        booksResponse.setIsLast(bookPages.isLast());
        booksResponse.setHasNext(bookPages.hasNext());
        booksResponse.setHasPrevious(bookPages.hasPrevious());
        booksResponse.setCurrentPage(bookPages.getNumber());
        return booksResponse;
    }

    private void findAndUpdateAuthor(BookDto bookDto, Book book) {
        /*bookDto.getAuthorIds().forEach(authorId -> {
            Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
            book.getAuthors().add(author);
        });*/
        Author author = authorRepository.findById(bookDto.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author", "id", bookDto.getAuthorId()));
        book.getAuthors().add(author);
    }
}
