package com.mystarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyStarterAutoConfig {
	 @Bean
	    @ConditionalOnProperty(name = "my.feature.enabled", havingValue = "true")
	    public MyService myService() {
	        return new MyService();
	    }

}
