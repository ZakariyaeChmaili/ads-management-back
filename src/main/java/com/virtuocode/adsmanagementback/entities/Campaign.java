package com.virtuocode.adsmanagementback.entities;

import com.virtuocode.adsmanagementback.shared.status.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "campaign_table")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;


}
