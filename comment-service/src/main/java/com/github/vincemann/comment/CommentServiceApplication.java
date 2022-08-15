package com.github.vincemann.comment;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class CommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class, args);
    }

}
