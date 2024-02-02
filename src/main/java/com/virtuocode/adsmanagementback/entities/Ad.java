package com.virtuocode.adsmanagementback.entities;


import com.virtuocode.adsmanagementback.dto.AdDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "ad_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;
    private String description;

    private String imageUrl;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "compaign_id")
    private Campaign campaign;


    public AdDto toDto(){
        return new AdDto(this);
    }
}
