package com.blitzstriker.goodreads.services.impl;

import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.payload.user.RegisterDto;
import com.blitzstriker.goodreads.payload.user.UserDto;
import com.blitzstriker.goodreads.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;

    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(RegisterDto registerDto) {
        User user =  modelMapper.map(registerDto, User.class);
        return null;
    }
}
