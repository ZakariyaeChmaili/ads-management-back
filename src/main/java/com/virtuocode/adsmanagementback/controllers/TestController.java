package com.virtuocode.adsmanagementback.controllers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@RestController()
@RequestMapping("api/admin/")
public class TestController {



    @GetMapping("test")
    ResponseEntity admin() {

        return ResponseEntity.ok("this is admin space ");
    }


}
