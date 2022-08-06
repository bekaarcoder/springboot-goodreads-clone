package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.payload.user.RegisterDto;
import com.blitzstriker.goodreads.payload.user.UserDto;

public interface UserService {
    UserDto createUser(RegisterDto registerDto);
}
