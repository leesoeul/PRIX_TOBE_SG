package com.prix.homepage.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "global")
@Getter
@Setter
public class GlobalProperties {
    private String path;
    private String pathErr;
}
