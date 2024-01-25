package com.virtuocode.adsmanagementback.services.UserService;


import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.repositories.AdRepo;
import com.virtuocode.adsmanagementback.repositories.UserRepo;
import com.virtuocode.adsmanagementback.shared.roles.UserRole;
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

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public UserDto addUser(User user) {
        try {
            user.setRole(UserRole.USER);
            User savedUser = this.userRepo.save(user);
            return mapToDto(savedUser);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(user);
        }
    }

    public List<UserDto> getUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto deleteUser(Long userId) {
        try {
            User userToDelete = this.userRepo.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException(userId));
            this.userRepo.delete(userToDelete);
            return mapToDto(userToDelete);
        } catch (Exception e) {
            throw new EntityFailedToDeleteException(userId);
        }
    }

    @Override
    public UserDto updateUser(UserDto user) {
        try {
            User userToUpdate = this.userRepo.findById(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException(user.getId()));

            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
            User updatedUser = this.userRepo.save(userToUpdate);
            return mapToDto(updatedUser);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(user);
        }
    }

    @Override
    public UserDto findUser(Long userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));
        return mapToDto(user);
    }
}

