package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.entity.ReadingStatus;
import com.blitzstriker.goodreads.payload.userbook.UserBookDto;
import com.blitzstriker.goodreads.payload.userbook.UserBookResponse;

public interface UserBookService {
    UserBookResponse addBookToLibrary(UserBookDto userBookDto, Long bookId);
    UserBookResponse updateStatus(UserBookDto userBookDto, Long bookId, Long userId);
    void deleteBookFromLibrary(Long bookId, Long userId);

    void addBookToReadingShelf(Long bookId, ReadingStatus status);
}
