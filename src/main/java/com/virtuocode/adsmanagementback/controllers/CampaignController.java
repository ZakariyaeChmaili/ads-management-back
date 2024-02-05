package com.virtuocode.adsmanagementback.controllers;

import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.services.CampaignService.CampaignService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    CampaignController(CampaignService campaignService) {

        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<List<CampaignDto>> getCampaigns() {
        List<CampaignDto> campaigns = campaignService.getCampaigns();
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDto> getCampaign(@PathVariable
                                                       @Positive
                                                       @NotNull
                                                       Long id) {
        CampaignDto campaign = campaignService.getCampaign(id);
        if (campaign != null) {
            return new ResponseEntity<>(campaign, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CampaignDto> addCampaign(@Valid @RequestBody Campaign campaign) {
        CampaignDto newCampaign = campaignService.addCampaign(campaign);
        return new ResponseEntity<>(newCampaign, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CampaignDto> deleteCampaign(@PathVariable
                                                      @Positive
                                                      @NotNull
                                                      Long id) {
        CampaignDto deletedCampaign = campaignService.deleteCampaign(id);
        if (deletedCampaign != null) {
            return new ResponseEntity<>(deletedCampaign, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<CampaignDto> updateCampaign(@Valid @RequestBody Campaign campaign) {
        CampaignDto updatedCampaign = campaignService.updateCampaign(campaign);
        return new ResponseEntity<>(updatedCampaign, HttpStatus.OK);
    }
}
