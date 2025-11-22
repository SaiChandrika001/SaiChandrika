package com.number.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
@Configuration(proxyBeanMethods = false)
public class SwaggerDocumentConfiguration {


	@Bean
     OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("CaseWorker Application")
                .version("1.0")
                .description("API documentation for Caseworker"));
    }
}
