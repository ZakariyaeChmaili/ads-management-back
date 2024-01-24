package com.virtuocode.adsmanagementback.services.CampaignService;

import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.repositories.CampaignRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService implements ICampaignService{

    private final CampaignRepo campaignRepo;

    CampaignService(CampaignRepo campaignRepo){
        this.campaignRepo = campaignRepo;
    }
    @Override
    public List<CampaignDto> getCampaigns() {
        List<Campaign> campaigns = campaignRepo.findAll();
        return campaigns.stream()
                .map(campaign -> CampaignDto.builder()
                        .id(campaign.getId())
                        .title(campaign.getTitle())
                        .description(campaign.getDescription())
                        .status(campaign.getStatus())
                        .partner(campaign.getPartner())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public CampaignDto getCampaign(Long campaignId) {
        Optional<Campaign> optionalCampaign = campaignRepo.findById(campaignId);
        return optionalCampaign.map(campaign -> CampaignDto.builder()
                        .id(campaign.getId())
                        .title(campaign.getTitle())
                        .description(campaign.getDescription())
                        .status(campaign.getStatus())
                        .partner(campaign.getPartner())
                        .build())
                .orElse(null);
    }

    @Override
    public CampaignDto addCampaign(Campaign campaign) {
        Campaign savedCampaign = campaignRepo.save(campaign);
        return CampaignDto.builder()
                .id(savedCampaign.getId())
                .title(savedCampaign.getTitle())
                .description(savedCampaign.getDescription())
                .status(savedCampaign.getStatus())
                .partner(savedCampaign.getPartner())
                .build();
    }

    @Override
    public CampaignDto deleteCampaign(Long campaignId) {
        Optional<Campaign> optionalCampaign = campaignRepo.findById(campaignId);
        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            campaignRepo.deleteById(campaignId);
            return CampaignDto.builder()
                    .id(campaign.getId())
                    .title(campaign.getTitle())
                    .description(campaign.getDescription())
                    .status(campaign.getStatus())
                    .partner(campaign.getPartner())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public CampaignDto updateCampaign(Campaign campaign) {
        Campaign updatedCampaign = campaignRepo.save(campaign);
        return CampaignDto.builder()
                .id(updatedCampaign.getId())
                .title(updatedCampaign.getTitle())
                .description(updatedCampaign.getDescription())
                .status(updatedCampaign.getStatus())
                .partner(updatedCampaign.getPartner())
                .build();
    }


}
