package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.entity.Book;
import com.blitzstriker.goodreads.payload.book.BookDto;
import com.blitzstriker.goodreads.payload.book.BookResponse;
import com.blitzstriker.goodreads.payload.book.BooksResponse;

public interface BookService {
    BookResponse addBook(BookDto bookDto);
    BookResponse updateBook(BookDto bookDto, Long id);
    BookResponse getBook(Long id);
    void deleteBook(Long id);
    Book getBookById(Long id);

    BooksResponse getAllBooks(Integer pageNumber, Integer pageSize);
}
