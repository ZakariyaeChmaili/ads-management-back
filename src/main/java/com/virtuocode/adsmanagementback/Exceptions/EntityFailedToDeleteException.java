package com.virtuocode.adsmanagementback.Exceptions;

import lombok.Getter;

@Getter
public class EntityFailedToDeleteException extends RuntimeException{
    Object entity;
    public EntityFailedToDeleteException(Object entity){
        super("Entity Has Been Failed To Delete, potential foreign key constraint");
        this.entity = entity;

    }
}
