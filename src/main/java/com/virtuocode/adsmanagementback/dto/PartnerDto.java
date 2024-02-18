package com.virtuocode.adsmanagementback.dto;

import com.virtuocode.adsmanagementback.entities.Partner;
import com.virtuocode.adsmanagementback.entities.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

public class PartnerDto {
    private Long id;


    private String name;
    private String url;

    private String logo;

    private User user;
    public PartnerDto(Partner partner) {
        this.id = partner.getId();
        this.name = partner.getName();
        this.url = partner.getUrl();
        this.logo = partner.getLogo();
        this.user = partner.getUser();
    }
}
