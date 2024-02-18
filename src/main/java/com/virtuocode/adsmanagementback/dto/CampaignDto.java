package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Campaign;
import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.shared.status.Status;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

public class CampaignDto {
    private long id;

    private String title;

    private String description;

    private Status status;


    private Partner partner;

    public CampaignDto(Campaign campaign) {
        this.id = campaign.getId();
        this.title = campaign.getTitle();
        this.description = campaign.getDescription();
        this.status = campaign.getStatus();
        this.partner = campaign.getPartner();
    }
}
