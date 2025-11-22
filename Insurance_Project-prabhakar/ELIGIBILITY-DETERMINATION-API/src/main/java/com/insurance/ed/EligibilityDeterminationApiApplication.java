package com.insurance.ed;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableFeignClients
@EnableAdminServer
@EnableDiscoveryClient
public class EligibilityDeterminationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EligibilityDeterminationApiApplication.class, args);
    }

}
