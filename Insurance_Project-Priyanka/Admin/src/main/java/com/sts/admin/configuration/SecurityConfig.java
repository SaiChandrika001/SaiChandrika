package com.sts.admin.configuration;

import com.sts.admin.security.keycloakRealmRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // create JwtAuthenticationConverter and set our custom converter
        JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
        // if you want only the realm roles and client roles:
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(new keycloakRealmRoleConverter("admin-client"));
        // or to restrict to a specific client use:
        // jwtAuthConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter("admin-client"));

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**","/v3/api-docs/**","/swagger-ui/**","/docs/**").permitAll()
                        .requestMatchers("/admin/api/**").hasRole("ADMIN")                 // expects ROLE_ADMIN
                        .requestMatchers("/caseworker/api/**").hasAnyRole("ADMIN","CASEWORKER")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                );

        return http.build();
    }
}
