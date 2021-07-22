package com.khoab.tmademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class TmaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmaDemoApplication.class, args);
    }

}
