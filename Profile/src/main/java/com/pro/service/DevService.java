package com.pro.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevService {
	
    public DevService() {
        System.out.println("💤 DevService bean created lazily...");
    }

    public void run() {
        System.out.println("Running in DEV mode...");
    }

}
