package com.virtuocode.adsmanagementback.Exceptions;

import lombok.Getter;

@Getter
public class EntityFailedToSaveException extends RuntimeException{

    Object entity;
    public EntityFailedToSaveException(Object entity){
        super("Entity Has Been Failed To Save, potential wrong entity format");
        this.entity = entity;

    }


}
