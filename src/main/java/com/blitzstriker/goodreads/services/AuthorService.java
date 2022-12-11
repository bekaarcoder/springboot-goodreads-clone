package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.payload.author.AuthorDto;
import com.blitzstriker.goodreads.payload.author.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorDto createAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(AuthorDto authorDto, Long authorId);
    void deleteAuthor(Long authorId);
    AuthorDto getAuthor(Long authorId);
    List<AuthorResponse> findAuthorByName(String name);
}
