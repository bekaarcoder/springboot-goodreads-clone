package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.Book;
import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    UserBook findByBookAndUser(Book book, User user);
    List<UserBook> findUserBookByUser(User user);
}
