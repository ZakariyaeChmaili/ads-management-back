package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.entities.Platform;
import com.virtuocode.adsmanagementback.entities.PlatformCampaign;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformCampaignDto {


    private Long id;

    private Platform platform;

    private Campaign campaign;

    public PlatformCampaignDto(PlatformCampaign platformCampaign) {
        this.id = platformCampaign.getId();
        this.platform = platformCampaign.getPlatform();
        this.campaign = platformCampaign.getCampaign();
    }
}
