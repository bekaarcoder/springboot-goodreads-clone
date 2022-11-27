package com.blitzstriker.goodreads.repositories;

import com.blitzstriker.goodreads.entity.Shelf;
import com.blitzstriker.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    List<Shelf> findShelfByUser(User user);
}
