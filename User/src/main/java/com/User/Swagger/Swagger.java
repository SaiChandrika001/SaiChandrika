package com.User.Swagger;

import org.springframework.context.annotation.Bean;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


public class Swagger {
	
	@Bean
	OpenAPI customOpenAPI() {
		
		return new OpenAPI().info(new Info().title("User Application").
				version("1.0").description("API for managing User Application"));
		
		
	}

}
