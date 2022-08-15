package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
