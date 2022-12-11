package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByFirstNameAndLastName(String firstName, String lastName);
    List<Author> findAuthorByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String keyword, String keyword2);
}
