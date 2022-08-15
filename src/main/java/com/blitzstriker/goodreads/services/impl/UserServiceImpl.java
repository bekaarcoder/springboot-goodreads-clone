package com.blitzstriker.goodreads.services.impl;

import com.blitzstriker.goodreads.config.AppConstants;
import com.blitzstriker.goodreads.entity.Role;
import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.exceptions.ItemAlreadyExistsException;
import com.blitzstriker.goodreads.exceptions.ResourceNotFoundException;
import com.blitzstriker.goodreads.payload.user.RegisterDto;
import com.blitzstriker.goodreads.payload.user.UserDto;
import com.blitzstriker.goodreads.payload.user.UserResponse;
import com.blitzstriker.goodreads.repositories.RoleRepository;
import com.blitzstriker.goodreads.repositories.UserRepository;
import com.blitzstriker.goodreads.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto createUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ItemAlreadyExistsException("User already exists with email " + registerDto.getEmail());
        }

        User user =  modelMapper.map(registerDto, User.class);
        //Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set role
        Role role = roleRepository.findById(AppConstants.ROLE_USER).orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstants.ROLE_USER));
        user.getRoles().add(role);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return modelMapper.map(user, UserResponse.class);
    }
}
