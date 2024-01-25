package com.virtuocode.adsmanagementback.controllers;

import com.virtuocode.adsmanagementback.dto.PlatformDto;
import com.virtuocode.adsmanagementback.entities.Platform;
import com.virtuocode.adsmanagementback.services.PlatformService.IPlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/platform")
public class PlatformController {


    private final IPlatformService platformService;

    PlatformController(IPlatformService platformService){

        this.platformService = platformService;
    }
    @PostMapping
    public ResponseEntity<PlatformDto> addPlatform(@RequestBody Platform platform) {
        PlatformDto newPlatform = platformService.addPlatform(platform);
        return new ResponseEntity<>(newPlatform, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlatformDto> deletePlatform(@PathVariable Long id) {
        PlatformDto deletedPlatform = platformService.deletePlatform(id);
        if (deletedPlatform != null) {
            return new ResponseEntity<>(deletedPlatform, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<PlatformDto> updatePlatform(@RequestBody Platform platform) {
        PlatformDto updatedPlatform = platformService.updatePlatform(platform);
        return new ResponseEntity<>(updatedPlatform, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PlatformDto>> getPlatforms() {
        List<PlatformDto> platforms = platformService.getPlatforms();
        return new ResponseEntity<>(platforms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformDto> getPlatform(@PathVariable Long id) {
        PlatformDto platform = platformService.getPlatform(id);
        if (platform != null) {
            return new ResponseEntity<>(platform, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
