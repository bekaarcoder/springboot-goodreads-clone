package com.blitzstriker.goodreads.services.impl;

import com.blitzstriker.goodreads.config.AppConstants;
import com.blitzstriker.goodreads.entity.Book;
import com.blitzstriker.goodreads.entity.ReadingStatus;
import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.entity.UserBook;
import com.blitzstriker.goodreads.exceptions.ApiException;
import com.blitzstriker.goodreads.exceptions.ResourceNotFoundException;
import com.blitzstriker.goodreads.payload.userbook.UserBookDto;
import com.blitzstriker.goodreads.payload.userbook.UserBookResponse;
import com.blitzstriker.goodreads.repositories.BookRepository;
import com.blitzstriker.goodreads.repositories.UserBookRepository;
import com.blitzstriker.goodreads.repositories.UserRepository;
import com.blitzstriker.goodreads.services.BookService;
import com.blitzstriker.goodreads.services.UserBookService;
import com.blitzstriker.goodreads.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookService bookService;

    @Override
    public UserBookResponse addBookToLibrary(UserBookDto userBookDto, Long bookId) {
        User user = userService.getLoggedInUser();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        UserBook userBook = modelMapper.map(userBookDto, UserBook.class);
        userBook.setUser(user);
        userBook.setBook(book);
        if (userBookDto.getStatus() != null && Objects.equals(userBookDto.getStatus().toString(), AppConstants.CR)) {
            userBook.setStartDate(new Date());
        } else if (userBookDto.getStatus() != null && Objects.equals(userBookDto.getStatus().toString(), AppConstants.READ)) {
            userBook.setEndDate(new Date());
        } else if (userBookDto.getStatus() == null){
            userBook.setStatus(ReadingStatus.valueOf(AppConstants.WANT));
        }
        UserBook savedUserBook = userBookRepository.save(userBook);
        UserBookResponse response =  modelMapper.map(savedUserBook, UserBookResponse.class);
        response.setStatus(savedUserBook.getStatus().name());
        return response;
    }

    @Override
    public UserBookResponse updateStatus(UserBookDto userBookDto, Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        UserBook userBook = userBookRepository.findByBookAndUser(book, user);
        if(userBook == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Book is not added to your list.");
        }
        if(userBookDto.getStatus().toString().equals(AppConstants.CR)) {
            userBook.setStatus(ReadingStatus.valueOf(AppConstants.CR));
            userBook.setStartDate(new Date());
        } else if (userBookDto.getStatus().toString().equals(AppConstants.READ)) {
            userBook.setStatus(ReadingStatus.valueOf(AppConstants.READ));
            userBook.setEndDate(new Date());
        } else {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Some error occurred. Please try again");
        }
        UserBook updated = userBookRepository.save(userBook);
        return modelMapper.map(updated, UserBookResponse.class);
    }

    @Override
    public void deleteBookFromLibrary(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        UserBook userBook = userBookRepository.findByBookAndUser(book, user);
        if(userBook == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Book is not added to your list.");
        }
        userBookRepository.delete(userBook);
    }

    @Override
    public void addBookToReadingShelf(Long bookId, ReadingStatus status) {
        User user = userService.getLoggedInUser();
        Book book = bookService.getBookById(bookId);
        UserBook existingUserBook = userBookRepository.findByBookAndUser(book, user);
        if (existingUserBook != null) {
            existingUserBook.setStatus(status);
            userBookRepository.save(existingUserBook);
        } else {
            UserBook newUserBook = new UserBook();
            newUserBook.setUser(user);
            newUserBook.setBook(book);
            newUserBook.setStatus(status);
            userBookRepository.save(newUserBook);
        }
    }
}
