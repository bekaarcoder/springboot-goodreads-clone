package com.blitzstriker.goodreads.payload.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotEmpty(message = "Book name is required")
    private String name;

    private Integer pages;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date publishedDate;

    private String coverImage;
    private String description;

    @NotEmpty(message = "Author name is required")
    private String authorFirstName;

    @NotEmpty(message = "Author name is required")
    private String authorLastName;
}
