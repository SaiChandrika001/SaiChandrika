package com.circular.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class Service8 {

	@EventListener
	 public void handleCustomEvent(CustomEvent event) {
        System.out.println("Service8: received event -> " + event.getMessage());
        System.out.println("Service8: responding back to Service9");
    }
	
}
