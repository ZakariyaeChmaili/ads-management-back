package com.virtuocode.adsmanagementback.repositories;

import com.virtuocode.adsmanagementback.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepo extends JpaRepository<Campaign,Long> {
}
