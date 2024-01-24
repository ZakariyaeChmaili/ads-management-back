package com.virtuocode.adsmanagementback.dto;

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
}
