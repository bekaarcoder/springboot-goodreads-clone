package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.payload.author.AuthorDto;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(AuthorDto authorDto, Long authorId);
    void deleteAuthor(Long authorId);
    AuthorDto getAuthor(Long authorId);
}
