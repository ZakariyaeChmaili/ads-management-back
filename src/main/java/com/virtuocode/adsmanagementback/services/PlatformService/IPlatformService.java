package com.virtuocode.adsmanagementback.services.PlatformService;

import com.virtuocode.adsmanagementback.dto.PlatformDto;
import com.virtuocode.adsmanagementback.entities.Platform;

import java.util.List;

public interface IPlatformService {

    PlatformDto addPlatform(Platform platform);

    PlatformDto deletePlatform(Long platformId);

    PlatformDto updatePlatform(Platform platform);

    List<PlatformDto> getPlatforms();

    PlatformDto getPlatform(Long platformId);
}
