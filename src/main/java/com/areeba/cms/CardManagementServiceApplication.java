package com.areeba.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CardManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardManagementServiceApplication.class, args);
    }

}
