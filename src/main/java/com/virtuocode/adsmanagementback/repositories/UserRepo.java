package com.virtuocode.adsmanagementback.repositories;

import com.virtuocode.adsmanagementback.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findUserByUsername(String username);

}
