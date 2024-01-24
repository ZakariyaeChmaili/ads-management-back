package com.virtuocode.adsmanagementback.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "ad_table")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "compaign_id")
    private Campaign campaign;
}
