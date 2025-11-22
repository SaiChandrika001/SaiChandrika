package com.circular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class Service9 {
	
	  @Autowired
	    private ApplicationEventPublisher publisher;
	  
	  public void process() {
		  System.out.println("Service9: doing some work...");
	        // Instead of calling ServiceB directly, publish event
	        publisher.publishEvent(new CustomEvent(this, "Event from Service9"));
	        System.out.println("ServiceA: event published to notify Service8");
	    }
	  }


