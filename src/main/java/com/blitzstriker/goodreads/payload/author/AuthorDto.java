package com.blitzstriker.goodreads.payload.author;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthorDto {
    private Long id;

    @NotEmpty(message = "Name is required")
    private String firstName;

    private String lastName;
    private String image;
    private String bio;
}
