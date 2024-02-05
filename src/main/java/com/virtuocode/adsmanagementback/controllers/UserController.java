package com.virtuocode.adsmanagementback.controllers;

import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.services.UserService.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@Tag(
        name = "User Controller",
        description = "this is controller for User to perform crud operations // Update api is Being hidden")
public class UserController {


    private final IUserService userService;

    UserController(IUserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody User user) {
        UserDto newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Hidden
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody User user) {
        UserDto userDto = this.userService.updateUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(
            @PathVariable
            @Min(1)
            Long id) {
        UserDto userDto = this.userService.deleteUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


}
