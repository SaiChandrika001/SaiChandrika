package com.circular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CircularBreakingApplication {

	public static void main(String[] args) {
		
	
		 var context =SpringApplication.run(CircularBreakingApplication.class, args);
		 
		 // Trigger interaction
        context.getBean(com.circular.service.Service9.class).process();
    
	}

}
