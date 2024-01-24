package com.virtuocode.adsmanagementback.repositories;

import com.virtuocode.adsmanagementback.entities.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepo extends JpaRepository<Ad,Long> {
}
