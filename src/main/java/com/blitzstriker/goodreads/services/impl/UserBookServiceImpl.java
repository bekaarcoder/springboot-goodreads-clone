package com.blitzstriker.goodreads.services.impl;

import com.blitzstriker.goodreads.config.AppConstants;
import com.blitzstriker.goodreads.entity.Book;
import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.entity.UserBook;
import com.blitzstriker.goodreads.exceptions.ApiException;
import com.blitzstriker.goodreads.exceptions.ResourceNotFoundException;
import com.blitzstriker.goodreads.payload.userbook.UserBookDto;
import com.blitzstriker.goodreads.payload.userbook.UserBookResponse;
import com.blitzstriker.goodreads.repositories.BookRepository;
import com.blitzstriker.goodreads.repositories.UserBookRepository;
import com.blitzstriker.goodreads.repositories.UserRepository;
import com.blitzstriker.goodreads.services.UserBookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserBookServiceImpl implements UserBookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final ModelMapper modelMapper;

    public UserBookServiceImpl(BookRepository bookRepository, UserRepository userRepository, UserBookRepository userBookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userBookRepository = userBookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserBookResponse addBookToLibrary(UserBookDto userBookDto, Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        UserBook userBook = modelMapper.map(userBookDto, UserBook.class);
        userBook.setUser(user);
        userBook.setBook(book);
        UserBook savedUserBook = userBookRepository.save(userBook);
        return modelMapper.map(savedUserBook, UserBookResponse.class);
    }

    @Override
    public UserBookResponse updateStatus(UserBookDto userBookDto, Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        UserBook userBook = userBookRepository.findByBookAndUser(book, user);
        if(userBook == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Book is not added to your list.");
        }
        if(userBookDto.getStatus().equals(AppConstants.CR)) {
            userBook.setStatus(AppConstants.CR);
            userBook.setStartDate(new Date());
        } else if (userBookDto.getStatus().equals(AppConstants.READ)) {
            userBook.setStatus(AppConstants.READ);
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
}
