package com.virtuocode.adsmanagementback.services.UserService;

import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;

import java.util.List;

public interface IUserService {


     UserDto addUser(User user);
     List<UserDto> getUsers();

     UserDto deleteUser(Long userId);

     UserDto updateUser(User user);

     UserDto findUser(Long userId);

     void updateLastActivity(User user);
}
