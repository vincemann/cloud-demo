package com.github.vincemann.localposting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class LocalPostingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalPostingServiceApplication.class, args);
    }

}
