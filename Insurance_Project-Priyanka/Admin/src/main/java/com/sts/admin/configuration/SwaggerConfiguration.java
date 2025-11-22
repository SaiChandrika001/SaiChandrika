package com.sts.admin.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class SwaggerConfiguration {



    @Bean
    public OpenAPI adminCustomOpenAPI() {
        String keycloakAuthUrl = "http://localhost:8180/realms/myrealm/protocol/openid-connect/auth";
        String keycloakTokenUrl = "http://localhost:8180/realms/myrealm/protocol/openid-connect/token";

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("keycloakOAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .description("Login via Keycloak")
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl(keycloakAuthUrl)
                                                        .tokenUrl(keycloakTokenUrl)
                                                        .scopes(new Scopes()
                                                                .addString("openid", "OpenID Connect scope")
                                                                .addString("profile", "User profile information")
                                                                .addString("email", "User email"))))))
                .addSecurityItem(new SecurityRequirement().addList("keycloakOAuth", List.of("openid")));
    }

}
