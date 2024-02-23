package com.virtuocode.adsmanagementback.services.UserService;


import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.repositories.AdRepo;
import com.virtuocode.adsmanagementback.repositories.UserRepo;
import com.virtuocode.adsmanagementback.shared.roles.UserRole;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;

        this.passwordEncoder = passwordEncoder;
    }

    public UserDto addUser(@Valid User user) {
        try {
            user.setRole(UserRole.USER);
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            User savedUser = this.userRepo.save(user);
            return savedUser.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(user);
        }
    }

    public List<UserDto> getUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(User::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto deleteUser(Long userId) {
        User userToDelete = this.userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));
        try {
            this.userRepo.delete(userToDelete);
            return userToDelete.toDto();
        } catch (Exception e) {
            throw new EntityFailedToDeleteException(userId);
        }
    }

    @Override
    public UserDto updateUser(@Valid User user) {
            this.userRepo.findById(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException(user.getId()));
        try {
            User updatedUser = this.userRepo.save(user);
            return updatedUser.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(user);
        }
    }


    @Override
    public UserDto findUser(Long userId) {
        return this.userRepo.findById(userId)
                .map(User::toDto)
                .orElseThrow(() -> new EntityNotFoundException(userId));
    }

    @Override
    public void updateLastActivity(User user) {
        user.setLastActivity(new Date());
        this.userRepo.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user with username: "+username+" not found."));
    }



}

