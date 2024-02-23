package com.virtuocode.adsmanagementback.Exceptions;

public class InvalidOrMissingTokenException extends RuntimeException {

    public InvalidOrMissingTokenException(){
        super("Invalid or Missing Token");
    }
}
