package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.User;
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
public class PartnerDto {
    private Long id;


    private String name;
    private String url;

    private String logo;

    private User user;
}
