package com.virtuocode.adsmanagementback.entities;

import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.dto.PlatformDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "platform_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String image;

    private String url;

    public PlatformDto toDto(){
        return new PlatformDto(this);
    };


}
