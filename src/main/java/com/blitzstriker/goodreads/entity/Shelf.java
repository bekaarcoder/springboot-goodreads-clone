package com.blitzstriker.goodreads.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Shelf")
@Table(name = "shelf")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shelf {
    @Id
    @SequenceGenerator(
            name = "shelf_sequence",
            sequenceName = "shelf_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shelf_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "shelf_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "shelf", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<BookShelf> bookShelves = new ArrayList<>();

    public void addBookShelf(BookShelf bookShelf) {
        if (!this.bookShelves.contains(bookShelf)) {
            this.bookShelves.add(bookShelf);
        }
    }

    public void removeBookShelf(BookShelf bookShelf) {
        this.bookShelves.remove(bookShelf);
    }
}
