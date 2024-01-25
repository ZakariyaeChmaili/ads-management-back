package com.virtuocode.adsmanagementback.config.swaggerConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

import java.nio.file.WatchEvent;

@OpenAPIDefinition(info = @Info(
        title = "Ads Management Api",
        description = "This is a Documentation for Ads Management Api <br>" +
                "GitHub : <a>https://github.com/ZakariyaeChmaili/ads-management-back</a>",
        version = "0.1"
))

public class OpenApiConfig {
}
