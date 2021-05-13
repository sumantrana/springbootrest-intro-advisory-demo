package com.hkjc.springtraining.springbootrestintro;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SpringbootrestIntroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootrestIntroApplication.class, args);
    }

}
