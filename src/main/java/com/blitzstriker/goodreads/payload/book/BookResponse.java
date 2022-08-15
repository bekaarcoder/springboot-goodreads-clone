package com.blitzstriker.goodreads.payload.book;

import com.blitzstriker.goodreads.entity.Author;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String name;
    private Integer pages;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date publishedDate;

    private String coverImage;
    private String description;
    private Set<Author> authors;
}
