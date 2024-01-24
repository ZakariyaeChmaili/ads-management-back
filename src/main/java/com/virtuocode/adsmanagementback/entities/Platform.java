package com.virtuocode.adsmanagementback.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "platform_table")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String url;




}
