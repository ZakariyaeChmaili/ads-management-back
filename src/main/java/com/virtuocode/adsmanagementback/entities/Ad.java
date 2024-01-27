package com.virtuocode.adsmanagementback.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity(name = "ad_table")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String title;
    private String description;

    private String imageUrl;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "compaign_id")
    private Campaign campaign;
}
