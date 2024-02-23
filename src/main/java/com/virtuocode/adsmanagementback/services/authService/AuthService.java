package com.virtuocode.adsmanagementback.services.authService;

import com.virtuocode.adsmanagementback.Exceptions.WrongCredentialException;
import com.virtuocode.adsmanagementback.dto.LoginRequestDto;
import com.virtuocode.adsmanagementback.dto.UserDto;
import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.services.UserService.UserService;
import com.virtuocode.adsmanagementback.services.jwtService.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;
    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public String login(LoginRequestDto loginRequestDto) {

        Authentication authUser = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        try {
            this.authenticationManager.authenticate(authUser);
            User user = userService.loadUserByUsername(loginRequestDto.getUsername());
            userService.updateLastActivity(user);
            return jwtService.generateToken(user);
        } catch (Exception e) {
            throw new WrongCredentialException(loginRequestDto);
        }
    }

    public UserDto register(User user) {
        return userService.addUser(user);
    }


    public void authenticateUser(User user){
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }


}
