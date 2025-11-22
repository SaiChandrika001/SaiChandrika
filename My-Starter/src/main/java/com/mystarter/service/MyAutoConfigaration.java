package com.mystarter.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "my.service",
name = "enabled",
havingValue = "true")

public class MyAutoConfigaration {

	@Bean
	public MyService myService() {
		return new MyService();
	}
}
