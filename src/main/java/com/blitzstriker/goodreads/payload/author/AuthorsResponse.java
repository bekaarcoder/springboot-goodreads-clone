package com.blitzstriker.goodreads.payload.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorsResponse {
    private List<AuthorResponse> authors;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean isLast;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer currentPage;
}
