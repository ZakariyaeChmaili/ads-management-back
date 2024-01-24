package com.virtuocode.adsmanagementback.services.UserService;


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


    public UserDto addUser(User user) {
        try {
            user.setRole(UserRole.USER.getValue());
            User u = this.userRepo.save(user);
            UserDto userDto = UserDto.builder()
                    .id(u.getId())
                    .username(u.getUsername())
                    .role(UserRole.USER.getValue())
                    .build();
            return userDto;

        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return null;
        }
    }


    public List<UserDto> getUsers() {
        try {
            List<User> users = this.userRepo.findAll();
            return users.stream().map(u -> UserDto.builder()
                    .id(u.getId())
                    .username(u.getUsername())
                    .role(u.getRole())
                    .build()).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDto deleteUser(Long userId) {
        try {
            Optional<User> findedUser = this.userRepo.findById(userId);
            if (findedUser.isPresent()) {
                User userToDelete = findedUser.get();
                this.userRepo.delete(userToDelete);
                return UserDto.builder()
                        .id(userToDelete.getId())
                        .username(userToDelete.getUsername())
                        .build();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public UserDto updateUser(UserDto user) {

        try {
            Optional<User> findedUser = this.userRepo.findById(user.getId());
            if (findedUser.isPresent()) {
                User userToUpdate = findedUser.get();
                userToUpdate.setUsername(user.getUsername());
                userToUpdate.setPassword(user.getPassword());
                this.userRepo.save(userToUpdate);
                return UserDto.builder()
                        .id(userToUpdate.getId())
                        .username(userToUpdate.getUsername())
                        .role(userToUpdate.getRole())
                        .build();
            }
            return null;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public UserDto findUser(Long userId) {
        Optional<User> findedUser = this.userRepo.findById(userId);
        if (findedUser.isPresent()) {
            User u = findedUser.get();
            return UserDto.builder()
                    .id(u.getId())
                    .username(u.getUsername())
                    .role(u.getRole())
                    .build();
        }else return null;

    }
}
