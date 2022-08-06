package com.blitzstriker.goodreads.controllers;

import com.blitzstriker.goodreads.exceptions.ApiException;
import com.blitzstriker.goodreads.payload.user.AuthResponse;
import com.blitzstriker.goodreads.payload.user.LoginDto;
import com.blitzstriker.goodreads.payload.user.RegisterDto;
import com.blitzstriker.goodreads.payload.user.UserDto;
import com.blitzstriker.goodreads.security.JwtTokenProvider;
import com.blitzstriker.goodreads.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${app.jwt-expiration-ms}")
    private int jwtExpirationInMs;

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterDto registerDto) {
        UserDto userDto = userService.createUser(registerDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid username or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
        String accessToken = jwtTokenProvider.generateToken(userDetails);
        return new ResponseEntity<>(new AuthResponse(accessToken), HttpStatus.OK);
    }
}
