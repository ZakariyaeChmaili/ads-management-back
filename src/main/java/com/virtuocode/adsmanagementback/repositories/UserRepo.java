package com.virtuocode.adsmanagementback.repositories;

import com.virtuocode.adsmanagementback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
