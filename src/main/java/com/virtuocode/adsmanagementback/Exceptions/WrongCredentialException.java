package com.virtuocode.adsmanagementback.Exceptions;

import com.virtuocode.adsmanagementback.dto.LoginRequestDto;

public class WrongCredentialException extends RuntimeException {


    public WrongCredentialException(LoginRequestDto loginRequestDto){
        super("Wrong Credentials, username: "+loginRequestDto.getUsername()+" or password: "+loginRequestDto.getPassword()+" is incorrect.");
    }

    public WrongCredentialException(){
        super("Wrong Credentials");
    }
}
