package com.blitzstriker.goodreads.services;

import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.payload.user.RegisterDto;
import com.blitzstriker.goodreads.payload.user.UserDto;
import com.blitzstriker.goodreads.payload.user.UserResponse;
import com.blitzstriker.goodreads.payload.user.UserRoleDto;

public interface UserService {
    UserDto createUser(RegisterDto registerDto);
    UserResponse getUserById(Long userId);
    User getLoggedInUser();
    UserResponse updateUserRole(UserRoleDto userRoleDto, Long userId);
}
