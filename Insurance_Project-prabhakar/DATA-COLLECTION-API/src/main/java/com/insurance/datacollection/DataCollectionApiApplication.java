package com.insurance.datacollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DataCollectionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCollectionApiApplication.class, args);
    }

}
