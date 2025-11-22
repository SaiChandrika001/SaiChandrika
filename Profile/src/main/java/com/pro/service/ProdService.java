package com.pro.service;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service 
@Profile("Prod")
public class ProdService {
	

    public ProdService() {
        System.out.println("🚀 ProdService bean eagerly created at startup!");
    }

    public void run() {
        System.out.println("Running in PROD mode...");
    }

}
