package com.virtuocode.adsmanagementback.controllers;

import com.virtuocode.adsmanagementback.dto.AdDto;
import com.virtuocode.adsmanagementback.entities.Ad;
import com.virtuocode.adsmanagementback.services.AdSerivce.IAdService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ad")
public class AdController {

    private final IAdService adService;

    AdController(IAdService adService) {
        this.adService = adService;
    }

    @PostMapping
    public ResponseEntity<AdDto> addAd(@Valid @RequestBody Ad ad) {
        AdDto newAd = adService.addAd(ad);
        return new ResponseEntity<>(newAd, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdDto> deleteAd(@PathVariable
                                          @Positive
                                          @NotNull
                                          Long id) {
        AdDto deletedAd = adService.deleteAd(id);
        if (deletedAd != null) {
            return new ResponseEntity<>(deletedAd, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<AdDto> updateAd(@Valid @RequestBody
                                              @Positive
                                              @NotNull
                                              Ad ad) {
        AdDto updatedAd = adService.updateAd(ad);
        return new ResponseEntity<>(updatedAd, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AdDto>> getAds() {
        List<AdDto> ads = adService.getAds();
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> getAd(@PathVariable
                                           @Positive
                                           @NotNull
                                           Long id) {
        AdDto ad = adService.getAd(id);
        if (ad != null) {
            return new ResponseEntity<>(ad, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
