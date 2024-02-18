package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Platform;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
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
