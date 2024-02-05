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
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepo userRepo;

    UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDto addUser(@Valid User user) {
        System.out.println(user.getUsername());
        try {
            user.setRole(UserRole.USER);
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
        try {
            User userToUpdate = this.userRepo.findById(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException(user.getId()));

            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
            User updatedUser = this.userRepo.save(userToUpdate);
            return updatedUser.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(user);
        }
    }

    @Override
    public UserDto findUser(Long userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));
        return user.toDto();
    }
}

