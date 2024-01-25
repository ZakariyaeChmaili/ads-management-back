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

    private CampaignDto mapToDto(Campaign campaign) {
        return CampaignDto.builder()
                .id(campaign.getId())
                .title(campaign.getTitle())
                .description(campaign.getDescription())
                .status(campaign.getStatus())
                .partner(campaign.getPartner())
                .build();
    }

    @Override
    public List<CampaignDto> getCampaigns() {
            List<Campaign> campaigns = campaignRepo.findAll();
            return campaigns.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
    }

    @Override
    public CampaignDto getCampaign(Long campaignId) {
        try {
            Campaign campaign = campaignRepo.findById(campaignId)
                    .orElseThrow(() -> new EntityNotFoundException(campaignId));
            return mapToDto(campaign);
        } catch (Exception e) {
            throw new EntityNotFoundException(campaignId);
        }
    }

    @Override
    public CampaignDto addCampaign(Campaign campaign) {
        try {
            Campaign savedCampaign = campaignRepo.save(campaign);
            return mapToDto(savedCampaign);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(campaign);
        }
    }

    @Override
    public CampaignDto deleteCampaign(Long campaignId) {
        try {
            Campaign campaignToDelete = campaignRepo.findById(campaignId)
                    .orElseThrow(() -> new EntityNotFoundException(campaignId));

            campaignRepo.deleteById(campaignId);
            return mapToDto(campaignToDelete);
        } catch (Exception e) {
            throw new EntityFailedToDeleteException(campaignId);
        }
    }

    @Override
    public CampaignDto updateCampaign(Campaign campaign) {
        try {
            Campaign updatedCampaign = campaignRepo.save(campaign);
            return mapToDto(updatedCampaign);
        } catch (Exception e) {
            throw new EntityFailedToSaveException(campaign);
        }
    }
}
