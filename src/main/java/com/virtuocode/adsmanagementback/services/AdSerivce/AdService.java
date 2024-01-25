package com.virtuocode.adsmanagementback.services.AdSerivce;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
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

    private AdDto mapToDto(Ad ad) {
        return AdDto.builder()
                .id(ad.getId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .imageUrl(ad.getImageUrl())
                .campaign(ad.getCampaign())
                .build();
    }

    @Override
    public AdDto addAd(Ad ad) {
        try {
            Ad savedAd = adRepo.save(ad);
            return mapToDto(savedAd);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(ad);
        }
    }

    @Override
    public AdDto deleteAd(Long adId) {
        try {
            Ad adToDelete = adRepo.findById(adId)
                    .orElseThrow(() -> new EntityNotFoundException(adId));

            adRepo.deleteById(adId);
            return mapToDto(adToDelete);
        } catch (Exception e) {
            throw new EntityFailedToDeleteException(adId);
        }
    }

    @Override
    public AdDto updateAd(Ad ad) {
        try {
            Ad updatedAd = adRepo.save(ad);
            return mapToDto(updatedAd);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(ad);
        }
    }

    @Override
    public List<AdDto> getAds() {
            List<Ad> ads = adRepo.findAll();
            return ads.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
    }

    @Override
    public AdDto getAd(Long adId) {
        try {
            Ad ad = adRepo.findById(adId)
                    .orElseThrow(() -> new EntityNotFoundException(adId));
            return mapToDto(ad);
        } catch (Exception e) {
            throw new EntityNotFoundException(adId);
        }
    }
}
