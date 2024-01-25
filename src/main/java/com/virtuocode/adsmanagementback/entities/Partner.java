package com.virtuocode.adsmanagementback.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.ToString.Exclude;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


}
