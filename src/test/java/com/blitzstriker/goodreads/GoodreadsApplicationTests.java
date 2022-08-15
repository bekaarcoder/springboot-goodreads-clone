package com.blitzstriker.goodreads;

import com.blitzstriker.goodreads.entity.Author;
import com.blitzstriker.goodreads.entity.Book;
import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.entity.UserBook;
import com.blitzstriker.goodreads.repositories.AuthorRepository;
import com.blitzstriker.goodreads.repositories.BookRepository;
import com.blitzstriker.goodreads.repositories.UserBookRepository;
import com.blitzstriker.goodreads.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoodreadsApplicationTests {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    UserBookRepository userBookRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void testFindAuthor() {
        Author author = authorRepository.findByFirstNameAndLastName("Matt", "SAD");
        if (author == null) {
            System.out.println("No author found");
        } else {
            System.out.println(author);
        }
    }

    @Test
    void testFindUserBook() {
        Book book = bookRepository.findById(2L).get();
        User user = userRepository.findById(2L).get();
        UserBook userBook = userBookRepository.findByBookAndUser(book, user);
        System.out.println(userBook.getStatus());
    }

}
