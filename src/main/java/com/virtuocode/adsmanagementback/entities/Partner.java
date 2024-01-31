package com.virtuocode.adsmanagementback.entities;
import com.virtuocode.adsmanagementback.dto.CampaignDto;
import com.virtuocode.adsmanagementback.dto.PartnerDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "partner_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String url;

    private String logo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Transient
    public PartnerDto toDto(){
        return new PartnerDto(this);
    };
}
