package com.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.config.server.AppPropertices;

@SpringBootApplication
@EnableConfigurationProperties(AppPropertices.class)
//@EnableFeignClients
public class ConfigProperticesApplication {

	public static void main(String[] args) {
		
        ConfigurableApplicationContext context = SpringApplication.run(ConfigProperticesApplication.class, args);

        AppPropertices props = context.getBean(AppPropertices.class);
        System.out.println("Loaded properties:");
        System.out.println(props);
	
	}

}
