package com.blitzstriker.goodreads;

import com.blitzstriker.goodreads.entity.Author;
import com.blitzstriker.goodreads.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoodreadsApplicationTests {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void testFindAuthor() {
        Author author = authorRepository.findByFirstNameAndLastName("Matt", "SAD");
        if (author == null) {
            System.out.println("No author found");
        } else {
            System.out.println(author);
        }

    }

}
