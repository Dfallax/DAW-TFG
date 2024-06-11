package com.luislr.zerif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ZerifApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ZerifApplication.class, args);
    
    }

}
