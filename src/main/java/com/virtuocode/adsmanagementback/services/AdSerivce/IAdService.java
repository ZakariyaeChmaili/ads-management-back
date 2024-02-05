package com.virtuocode.adsmanagementback.services.AdSerivce;

import com.virtuocode.adsmanagementback.dto.AdDto;
import com.virtuocode.adsmanagementback.entities.Ad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdService {
    AdDto addAd(Ad ad);

    AdDto deleteAd(Long adId);

    AdDto updateAd(Ad ad);

    List<AdDto> getAds();


    AdDto getAd(Long adId);
}
