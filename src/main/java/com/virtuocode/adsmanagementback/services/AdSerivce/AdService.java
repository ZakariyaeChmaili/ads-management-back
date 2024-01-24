package com.virtuocode.adsmanagementback.services.AdSerivce;

import com.virtuocode.adsmanagementback.dto.AdDto;
import com.virtuocode.adsmanagementback.entities.Ad;
import com.virtuocode.adsmanagementback.repositories.AdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdService implements IAdService {


    private final AdRepo adRepo;

    AdService(AdRepo adRepo) {

        this.adRepo = adRepo;

    }
    @Override
    public AdDto addAd(Ad ad) {
        Ad savedAd = adRepo.save(ad);
        return AdDto.builder()
                .id(savedAd.getId())
                .title(savedAd.getTitle())
                .description(savedAd.getDescription())
                .imageUrl(savedAd.getImageUrl())
                .campaign(savedAd.getCampaign())
                .build();
    }

    @Override
    public AdDto deleteAd(Long adId) {
        Optional<Ad> optionalAd = adRepo.findById(adId);
        if (optionalAd.isPresent()) {
            Ad ad = optionalAd.get();
            adRepo.deleteById(adId);
            return AdDto.builder()
                    .id(ad.getId())
                    .title(ad.getTitle())
                    .description(ad.getDescription())
                    .imageUrl(ad.getImageUrl())
                    .campaign(ad.getCampaign())
                    .build();
        } else {

            return null;
        }
    }

    @Override
    public AdDto updateAd(Ad ad) {
        Ad updatedAd = adRepo.save(ad);
        return AdDto.builder()
                .id(updatedAd.getId())
                .title(updatedAd.getTitle())
                .description(updatedAd.getDescription())
                .imageUrl(updatedAd.getImageUrl())
                .campaign(updatedAd.getCampaign())
                .build();
    }

    @Override
    public List<AdDto> getAds() {
        List<Ad> ads = adRepo.findAll();
        return ads.stream()
                .map(ad -> AdDto.builder()
                        .id(ad.getId())
                        .title(ad.getTitle())
                        .description(ad.getDescription())
                        .imageUrl(ad.getImageUrl())
                        .campaign(ad.getCampaign())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public AdDto getAd(Long adId) {
        Optional<Ad> optionalAd = adRepo.findById(adId);
        return optionalAd.map(ad -> AdDto.builder()
                        .id(ad.getId())
                        .title(ad.getTitle())
                        .description(ad.getDescription())
                        .imageUrl(ad.getImageUrl())
                        .campaign(ad.getCampaign())
                        .build())
                .orElse(null);
    }










}