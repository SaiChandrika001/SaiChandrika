package com.mystarter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mystarter.service.MyService;

@SpringBootApplication
public class MyStarterApplication implements CommandLineRunner{

	private MyService myService;
	
	public static void main(String[] args) {
		SpringApplication.run(MyStarterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		  if (myService != null) {
	            System.out.println(myService.get());
	        } else {
	            System.out.println("MyService not loaded. Check your properties!");
	        }
		
	}

}
