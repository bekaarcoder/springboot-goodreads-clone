package com.blitzstriker.goodreads.services.impl;

import com.blitzstriker.goodreads.entity.Author;
import com.blitzstriker.goodreads.exceptions.ResourceNotFoundException;
import com.blitzstriker.goodreads.payload.author.AuthorDto;
import com.blitzstriker.goodreads.payload.author.AuthorResponse;
import com.blitzstriker.goodreads.repositories.AuthorRepository;
import com.blitzstriker.goodreads.services.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto, Author.class);
        Author savedAuthor = authorRepository.save(author);
        return modelMapper.map(savedAuthor, AuthorDto.class);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto, Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        author.setFirstName(authorDto.getFirstName() != null ? authorDto.getFirstName() : author.getFirstName());
        author.setLastName(authorDto.getLastName() != null ? authorDto.getLastName() : author.getLastName());
        author.setImage(authorDto.getImage() != null ? authorDto.getImage() : author.getImage());
        author.setBio(authorDto.getBio() != null ? authorDto.getBio() : author.getBio());
        Author updatedAuthor = authorRepository.save(author);
        return modelMapper.map(updatedAuthor, AuthorDto.class);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        authorRepository.delete(author);
    }

    @Override
    public AuthorDto getAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public List<AuthorResponse> findAuthorByName(String name) {
        List<Author> authors = authorRepository.findAuthorByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        return authors.stream().map(author -> modelMapper.map(author, AuthorResponse.class)).collect(Collectors.toList());
    }
}
