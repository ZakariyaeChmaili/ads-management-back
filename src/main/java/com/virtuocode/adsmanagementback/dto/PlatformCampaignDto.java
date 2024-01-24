package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.entities.Platform;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformCampaignDto {


    private Long id;

    private Platform platform_id;

    private Campaign campaign_id;
}
