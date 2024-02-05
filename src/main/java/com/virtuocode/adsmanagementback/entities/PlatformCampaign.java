package com.virtuocode.adsmanagementback.entities;

import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.dto.PlatformCampaignDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PlatformCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id")
    private Platform platform;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    public PlatformCampaignDto toDto(){
        return new PlatformCampaignDto(this);
    };
}
