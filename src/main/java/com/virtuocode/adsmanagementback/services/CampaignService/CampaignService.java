package com.virtuocode.adsmanagementback.services.CampaignService;

import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToDeleteException;
import com.virtuocode.adsmanagementback.Exceptions.EntityFailedToSaveException;
import com.virtuocode.adsmanagementback.Exceptions.EntityNotFoundException;
import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.repositories.CampaignRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService implements ICampaignService {

    private final CampaignRepo campaignRepo;

    CampaignService(CampaignRepo campaignRepo) {
        this.campaignRepo = campaignRepo;
    }

    @Override
    public List<CampaignDto> getCampaigns() {
            List<Campaign> campaigns = campaignRepo.findAll();
            return campaigns.stream()
                    .map(Campaign::toDto)
                    .collect(Collectors.toList());
    }

    @Override
    public CampaignDto getCampaign(Long campaignId) {
            Campaign campaign = campaignRepo.findById(campaignId)
                    .orElseThrow(() -> new EntityNotFoundException(campaignId));
            return campaign.toDto();

    }

    @Override
    public CampaignDto addCampaign(Campaign campaign) {
        try {
            Campaign savedCampaign = campaignRepo.save(campaign);
            return savedCampaign.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(campaign);
        }
    }

    @Override
    public CampaignDto deleteCampaign(Long campaignId) {
            Campaign campaignToDelete = campaignRepo.findById(campaignId)
                    .orElseThrow(() -> new EntityNotFoundException(campaignId));
        try {

            campaignRepo.deleteById(campaignId);
            return campaignToDelete.toDto();
        } catch (Exception e) {
            throw new EntityFailedToDeleteException(campaignId);
        }
    }

    @Override
    public CampaignDto updateCampaign(Campaign campaign) {
        try {
            Campaign updatedCampaign = campaignRepo.save(campaign);
            return updatedCampaign.toDto();
        } catch (Exception e) {
            throw new EntityFailedToSaveException(campaign);
        }
    }
}
