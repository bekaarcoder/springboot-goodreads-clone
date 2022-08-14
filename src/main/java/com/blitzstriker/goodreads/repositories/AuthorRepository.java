package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
