package com.blitzstriker.goodreads.payload.user;

import com.blitzstriker.goodreads.payload.userbook.UserBookResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<UserBookResponse> userBooks;
}
