package com.virtuocode.adsmanagementback.services.CampaignService;

import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.entities.Campaign;

import java.util.List;

public interface ICampaignService {



    List<CampaignDto> getCampaigns();

    CampaignDto getCampaign(Long campaignId);


    CampaignDto addCampaign(Campaign campaign) ;

    CampaignDto deleteCampaign(Long campaignId);

    CampaignDto updateCampaign(Campaign campaign);
}
