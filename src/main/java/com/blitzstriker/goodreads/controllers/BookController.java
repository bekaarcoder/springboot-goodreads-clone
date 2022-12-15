package com.blitzstriker.goodreads.controllers;

import com.blitzstriker.goodreads.config.AppConstants;
import com.blitzstriker.goodreads.entity.ReadingStatus;
import com.blitzstriker.goodreads.payload.ApiResponse;
import com.blitzstriker.goodreads.payload.book.BookDto;
import com.blitzstriker.goodreads.payload.book.BookResponse;
import com.blitzstriker.goodreads.payload.book.BooksResponse;
import com.blitzstriker.goodreads.payload.userbook.UserBookDto;
import com.blitzstriker.goodreads.payload.userbook.UserBookResponse;
import com.blitzstriker.goodreads.services.BookService;
import com.blitzstriker.goodreads.services.UserBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final UserBookService userBookService;

    public BookController(BookService bookService, UserBookService userBookService) {
        this.bookService = bookService;
        this.userBookService = userBookService;
    }

//    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping
    public ResponseEntity<BooksResponse> getAllBooks(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize
    ) {
        return new ResponseEntity<>(bookService.getAllBooks(pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping("/addToReadingShelf/{bookId}/{shelf}")
    public ResponseEntity<ApiResponse> addBookToReadingShelf(@PathVariable("bookId") Long bookId, @PathVariable("shelf") ReadingStatus shelf) {
        userBookService.addBookToReadingShelf(bookId, shelf);
        return new ResponseEntity<>(new ApiResponse("Book added successfully"), HttpStatus.OK);
    }

    @PostMapping("/{bookId}/add")
    public ResponseEntity<UserBookResponse> addBookToLibrary(@Valid @RequestBody UserBookDto userBookDto, @PathVariable("bookId") Long bookId) {
        UserBookResponse response = userBookService.addBookToLibrary(userBookDto, bookId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/add/{userId}")
    public ResponseEntity<UserBookResponse> UpdateStatus(@Valid @RequestBody UserBookDto userBookDto, @PathVariable("bookId") Long bookId, @PathVariable("userId") Long userId) {
        UserBookResponse response = userBookService.updateStatus(userBookDto, bookId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/add/{userId}")
    public ResponseEntity<ApiResponse> deleteBookFromLibrary(@PathVariable("bookId") Long bookId, @PathVariable("userId") Long userId) {
        userBookService.deleteBookFromLibrary(bookId, userId);
        return new ResponseEntity<>(new ApiResponse("Book removed successfully"), HttpStatus.OK);
    }
}
