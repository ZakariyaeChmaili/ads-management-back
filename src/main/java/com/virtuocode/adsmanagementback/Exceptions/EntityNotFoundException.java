package com.virtuocode.adsmanagementback.Exceptions;

public class EntityNotFoundException extends RuntimeException{


    public EntityNotFoundException(Long id){
        super("Entity with Id: "+id+" not Found");

    }
}
