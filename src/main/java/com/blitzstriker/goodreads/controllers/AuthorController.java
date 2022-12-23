package com.blitzstriker.goodreads.controllers;

import com.blitzstriker.goodreads.config.AppConstants;
import com.blitzstriker.goodreads.payload.ApiResponse;
import com.blitzstriker.goodreads.payload.author.AuthorDto;
import com.blitzstriker.goodreads.payload.author.AuthorResponse;
import com.blitzstriker.goodreads.payload.author.AuthorsResponse;
import com.blitzstriker.goodreads.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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


    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<AuthorResponse>> findAuthorsByName(@PathVariable("keyword") String keyword) {
        List<AuthorResponse> authors = authorService.findAuthorByName(keyword);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AuthorsResponse> getAllAuthors(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize
    ) {
        AuthorsResponse authorsResponse = authorService.getAllAuthors(pageNumber, pageSize);
        return new ResponseEntity<>(authorsResponse, HttpStatus.OK);
    }

}
