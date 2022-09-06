package com.github.vincemann.localposting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
//@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@RefreshScope
public class LocalPostingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalPostingServiceApplication.class, args);
    }

}
