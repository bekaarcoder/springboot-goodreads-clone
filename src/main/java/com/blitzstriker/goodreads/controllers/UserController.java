package com.blitzstriker.goodreads.controllers;

import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.payload.user.UserResponse;
import com.blitzstriker.goodreads.payload.user.UserRoleDto;
import com.blitzstriker.goodreads.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        UserResponse response = userService.getUserById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/updateRole")
    public ResponseEntity<UserResponse> updateUserRole(@Valid @RequestBody UserRoleDto userRoleDto, @PathVariable("id") Long id) {
        UserResponse user = userService.updateUserRole(userRoleDto, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
