package com.virtuocode.adsmanagementback.controllers;

import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.services.UserService.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {


    private final IUserService userService;

    UserController(IUserService userService){
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody User user){
        UserDto newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody User user){
        UserDto userDto = this.userService.updateUser(user);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id){
        UserDto userDto = this.userService.deleteUser(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }


}
