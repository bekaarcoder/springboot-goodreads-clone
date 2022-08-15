package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.payload.user.RegisterDto;
import com.blitzstriker.goodreads.payload.user.UserDto;
import com.blitzstriker.goodreads.payload.user.UserResponse;

public interface UserService {
    UserDto createUser(RegisterDto registerDto);
    UserResponse getUserById(Long userId);
}
