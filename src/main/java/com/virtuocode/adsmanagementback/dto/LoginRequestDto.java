package com.virtuocode.adsmanagementback.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
