package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Ad;
import com.virtuocode.adsmanagementback.entities.Campaign;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

public class AdDto {


    private Long id;

    private String title;
    private String description;

    private String imageUrl;

    private Campaign campaign;

    public AdDto(Ad ad) {
        this.id = ad.getId();
        this.title = ad.getTitle();
        this.description = ad.getDescription();
        this.imageUrl = ad.getImageUrl();
        this.campaign = ad.getCampaign();
    }
}
