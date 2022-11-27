package com.blitzstriker.goodreads.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "BookShelf")
@Table(name = "book_shelf")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookShelf {
    @Id
    @SequenceGenerator(
            name = "bookshelf_sequence",
            sequenceName = "bookshelf_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookshelf_sequence")
    @Column(name = "bookshelf_id", updatable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @CreationTimestamp
    @Column(name = "added_at")
    private Timestamp addedAt;

    public BookShelf(Shelf shelf, Book book) {
        this.shelf = shelf;
        this.book = book;
    }
}
