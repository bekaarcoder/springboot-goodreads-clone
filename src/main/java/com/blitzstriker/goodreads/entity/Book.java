package com.blitzstriker.goodreads.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer pages;
    private Date publishedDate;
    private String coverImage;

    @Column(length = 10000)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "books_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Rating> ratings;

    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<BookShelf> bookShelves = new ArrayList<>();

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    public void removeAuthors(Set<Author> authors) {
        this.authors.clear();
        authors.forEach(author -> author.getBooks().remove(this));
    }

    public void addRating(Rating rating) {
        if (!ratings.contains(rating)) {
            ratings.add(rating);
        }
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
    }

    public void updateRating(Rating rating) {
        if(ratings.contains(rating)) {
            int index = ratings.indexOf(rating);
            Rating exists = ratings.get(index);
            exists.setRating(rating.getRating());
        }
    }

    public void addBookShelf(BookShelf bookShelf) {
        if (!this.bookShelves.contains(bookShelf)) {
            this.bookShelves.add(bookShelf);
        }
    }

    public void removeBookShelf(BookShelf bookShelf) {
        this.bookShelves.remove(bookShelf);
    }
}
