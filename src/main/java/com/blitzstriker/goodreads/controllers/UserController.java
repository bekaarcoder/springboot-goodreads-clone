package com.blitzstriker.goodreads.controllers;

import com.blitzstriker.goodreads.entity.User;
import com.blitzstriker.goodreads.payload.user.UserResponse;
import com.blitzstriker.goodreads.payload.user.UserRoleDto;
import com.blitzstriker.goodreads.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

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

    @DeleteMapping("/{id}/deleteRole")
    public ResponseEntity<UserResponse> deleteUserRole(@Valid @RequestBody UserRoleDto userRoleDto, @PathVariable("id") Long id) {
        UserResponse user = userService.deleteUserRole(userRoleDto, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrentUser() {
        User user = userService.getLoggedInUser();
        UserResponse response = modelMapper.map(user, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
