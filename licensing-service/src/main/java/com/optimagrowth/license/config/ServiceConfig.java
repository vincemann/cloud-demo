package com.optimagrowth.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;


// reads property from property file, which will be read from config server and inject value into class member
@Configuration
@ConfigurationProperties(prefix = "example")
@Getter @Setter
public class ServiceConfig{

  private String property;
    
}