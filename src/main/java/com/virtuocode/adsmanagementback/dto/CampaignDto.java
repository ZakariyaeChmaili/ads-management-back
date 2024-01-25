package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.shared.status.Status;
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
public class CampaignDto {
    private long id;

    private String title;

    private String description;

    private Status status;


    private Partner partner;
}
