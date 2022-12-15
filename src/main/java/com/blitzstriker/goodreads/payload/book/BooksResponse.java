package com.blitzstriker.goodreads.payload.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BooksResponse {
    private List<BookResponse> books;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean isLast;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
