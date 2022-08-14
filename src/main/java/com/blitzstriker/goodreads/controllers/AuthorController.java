package com.blitzstriker.goodreads.controllers;

import com.blitzstriker.goodreads.payload.ApiResponse;
import com.blitzstriker.goodreads.payload.author.AuthorDto;
import com.blitzstriker.goodreads.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto authorDto) {
        AuthorDto createdAuthor = authorService.createAuthor(authorDto);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
         return new ResponseEntity<>(authorService.getAuthor(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(new ApiResponse("Author deleted successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@Valid @RequestBody AuthorDto authorDto, @PathVariable("id") Long id) {
        AuthorDto updatedAuthor = authorService.updateAuthor(authorDto, id);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

}
