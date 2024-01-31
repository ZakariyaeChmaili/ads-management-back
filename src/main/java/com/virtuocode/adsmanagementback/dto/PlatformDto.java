package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformDto {

    private Long id;

    private String name;

    private String image;

    private String url;

    public PlatformDto(Platform platform) {
        this.id = platform.getId();
        this.name = platform.getName();
        this.image = platform.getImage();
        this.url = platform.getUrl();
    }
}
