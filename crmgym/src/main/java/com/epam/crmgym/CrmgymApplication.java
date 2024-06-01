package com.epam.crmgym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CrmgymApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmgymApplication.class, args);
    }

}
