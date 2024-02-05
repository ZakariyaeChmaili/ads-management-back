package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.shared.roles.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String username;

//    private String password;


    private UserRole role;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
