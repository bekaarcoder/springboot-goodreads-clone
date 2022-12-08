package com.blitzstriker.goodreads.payload.user;

import com.blitzstriker.goodreads.entity.Role;
import com.blitzstriker.goodreads.payload.userbook.UserBookResponse;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
}
