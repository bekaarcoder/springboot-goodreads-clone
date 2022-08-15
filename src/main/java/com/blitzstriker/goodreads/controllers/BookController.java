package com.blitzstriker.goodreads.controllers;

import com.blitzstriker.goodreads.payload.ApiResponse;
import com.blitzstriker.goodreads.payload.book.BookDto;
import com.blitzstriker.goodreads.payload.book.BookResponse;
import com.blitzstriker.goodreads.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@Valid @RequestBody BookDto bookDto, @PathVariable("id") Long id) {
        BookResponse updatedBook = bookService.updateBook(bookDto, id);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable("id") Long id) {
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(new ApiResponse("Book deleted successfully"), HttpStatus.OK);
    }
}
