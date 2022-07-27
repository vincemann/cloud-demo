package com.github.vincemann.posting.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;


// reads property from property file, which will be read from config server and inject value into class member
@Configuration
@ConfigurationProperties(prefix = "commercial")
@Getter @Setter
public class CommercialProperties {

  private String text;
    
}