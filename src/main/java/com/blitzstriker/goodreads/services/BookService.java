package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.payload.book.BookDto;
import com.blitzstriker.goodreads.payload.book.BookResponse;

public interface BookService {
    BookResponse addBook(BookDto bookDto);
    BookResponse updateBook(BookDto bookDto, Long id);
    BookResponse getBook(Long id);
    void deleteBook(Long id);
}
