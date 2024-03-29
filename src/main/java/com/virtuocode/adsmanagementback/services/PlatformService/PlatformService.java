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
    @Override
    public PlatformDto addPlatform(Platform platform) {
        try {
            Platform savedPlatform = platformRepo.save(platform);
            return savedPlatform.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(platform);
        }
    }

    @Override
    public PlatformDto deletePlatform(Long platformId) {
            Platform platformToDelete = platformRepo.findById(platformId)
                    .orElseThrow(() -> new EntityNotFoundException(platformId));
        try {

            platformRepo.deleteById(platformId);
            return platformToDelete.toDto();
        } catch (Exception e) {
            throw new EntityFailedToDeleteException(platformId);
        }
    }

    @Override
    public PlatformDto updatePlatform(Platform platform) {
        platformRepo.findById(platform.getId())
                .orElseThrow(() -> new EntityNotFoundException(platform.getId()));
        try {
            Platform updatedPlatform = platformRepo.save(platform);
            return updatedPlatform.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(platform);
        }
    }

    @Override
    public List<PlatformDto> getPlatforms() {
            List<Platform> platforms = platformRepo.findAll();
            return platforms.stream()
                    .map(Platform::toDto)
                    .collect(Collectors.toList());
    }

    @Override
    public PlatformDto getPlatform(Long platformId) {
            Platform platform = platformRepo.findById(platformId)
                    .orElseThrow(() -> new EntityNotFoundException(platformId));
            return platform.toDto();

    }
}
