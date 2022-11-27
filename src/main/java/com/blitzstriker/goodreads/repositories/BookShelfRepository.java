package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.BookShelf;
import com.blitzstriker.goodreads.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookShelfRepository extends JpaRepository<BookShelf, Long> {
    List<BookShelf> findBookShelfByShelf(Shelf shelf);
}
