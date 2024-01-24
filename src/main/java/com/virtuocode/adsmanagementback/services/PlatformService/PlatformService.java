package com.virtuocode.adsmanagementback.services.PlatformService;

import com.virtuocode.adsmanagementback.dto.PlatformDto;
import com.virtuocode.adsmanagementback.entities.Platform;
import com.virtuocode.adsmanagementback.repositories.PlatformRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformService implements IPlatformService{


    private final PlatformRepo platformRepo;

    PlatformService(PlatformRepo platformRepo){

        this.platformRepo = platformRepo;
    }

    @Override
    public PlatformDto addPlatform(Platform platform) {
        Platform savedPlatform = platformRepo.save(platform);
        return PlatformDto.builder()
                .id(savedPlatform.getId())
                .name(savedPlatform.getName())
                .image(savedPlatform.getImage())
                .url(savedPlatform.getUrl())
                .build();
    }

    @Override
    public PlatformDto deletePlatform(Long platformId) {
        Optional<Platform> optionalPlatform = platformRepo.findById(platformId);
        if (optionalPlatform.isPresent()) {
            Platform platform = optionalPlatform.get();
            platformRepo.deleteById(platformId);
            return PlatformDto.builder()
                    .id(platform.getId())
                    .name(platform.getName())
                    .image(platform.getImage())
                    .url(platform.getUrl())
                    .build();
        } else {
            // Handle not found scenario, you can throw an exception or return a specific response
            return null;
        }
    }

    @Override
    public PlatformDto updatePlatform(Platform platform) {
        Platform updatedPlatform = platformRepo.save(platform);
        return PlatformDto.builder()
                .id(updatedPlatform.getId())
                .name(updatedPlatform.getName())
                .image(updatedPlatform.getImage())
                .url(updatedPlatform.getUrl())
                .build();
    }

    @Override
    public List<PlatformDto> getPlatforms() {
        List<Platform> platforms = platformRepo.findAll();
        return platforms.stream()
                .map(platform -> PlatformDto.builder()
                        .id(platform.getId())
                        .name(platform.getName())
                        .image(platform.getImage())
                        .url(platform.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public PlatformDto getPlatform(Long platformId) {
        Optional<Platform> optionalPlatform = platformRepo.findById(platformId);
        return optionalPlatform.map(platform -> PlatformDto.builder()
                        .id(platform.getId())
                        .name(platform.getName())
                        .image(platform.getImage())
                        .url(platform.getUrl())
                        .build())
                .orElse(null);
    }
}
