package com.virtuocode.adsmanagementback.shared.status;

public enum Status {

    DONE("DONE"),
    ONGOING("ONGOING"),
    DRAFT("DRAFT");

    private final String value;

    Status(String value){
        this.value= value;
    }

    public String getValue(){
        return this.value;
    }
}
