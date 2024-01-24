package com.virtuocode.adsmanagementback.shared.roles;

public enum UserRole {

    USER("USER"),
    ADMIN("ADMIN");
    private final String value;

    UserRole(String value) {
        this.value = value;
    }


    public String getValue(){
        return this.value;
    }


}
