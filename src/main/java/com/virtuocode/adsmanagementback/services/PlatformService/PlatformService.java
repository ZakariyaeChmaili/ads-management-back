package com.virtuocode.adsmanagementback.services.PlatformService;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.PlatformDto;
import com.virtuocode.adsmanagementback.entities.Platform;
import com.virtuocode.adsmanagementback.repositories.PlatformRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PlatformService implements IPlatformService {

    private final PlatformRepo platformRepo;

    PlatformService(PlatformRepo platformRepo) {
        this.platformRepo = platformRepo;
    }

    private PlatformDto mapToDto(Platform platform) {
        return PlatformDto.builder()
                .id(platform.getId())
                .name(platform.getName())
                .image(platform.getImage())
                .url(platform.getUrl())
                .build();
    }

    @Override
    public PlatformDto addPlatform(Platform platform) {
        try {
            Platform savedPlatform = platformRepo.save(platform);
            return mapToDto(savedPlatform);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(platform);
        }
    }

    @Override
    public PlatformDto deletePlatform(Long platformId) {
        try {
            Platform platformToDelete = platformRepo.findById(platformId)
                    .orElseThrow(() -> new EntityNotFoundException(platformId));

            platformRepo.deleteById(platformId);
            return mapToDto(platformToDelete);
        } catch (Exception e) {
            throw new EntityFailedToDeleteException(platformId);
        }
    }

    @Override
    public PlatformDto updatePlatform(Platform platform) {
        try {
            Platform updatedPlatform = platformRepo.save(platform);
            return mapToDto(updatedPlatform);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(platform);
        }
    }

    @Override
    public List<PlatformDto> getPlatforms() {
            List<Platform> platforms = platformRepo.findAll();
            return platforms.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
    }

    @Override
    public PlatformDto getPlatform(Long platformId) {
        try {
            Platform platform = platformRepo.findById(platformId)
                    .orElseThrow(() -> new EntityNotFoundException(platformId));
            return mapToDto(platform);
        } catch (Exception e) {
            throw new EntityNotFoundException(platformId);
        }
    }
}
