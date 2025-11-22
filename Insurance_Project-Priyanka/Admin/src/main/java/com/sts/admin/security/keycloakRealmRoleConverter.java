package com.sts.admin.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Convert Keycloak token claims into GrantedAuthority.
 * Produces authorities like ROLE_ADMIN, ROLE_CASEWORKER (uppercase).
 */
public class keycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String REALM_ACCESS = "realm_access";
    private static final String RESOURCE_ACCESS = "resource_access";

    private String clientId; // optional: to include client roles for a specific client

    public void KeycloakRealmRoleConverter() {
        this.clientId = null;
    }

    public void KeycloakRealmRoleConverter(String clientId) {
        this.clientId = clientId;
    }

    public keycloakRealmRoleConverter(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<String> roles = new HashSet<>();

        // 1) realm roles: token.claims.realm_access.roles -> [ "ADMIN", "USER" ]
        Object realmAccess = jwt.getClaim(REALM_ACCESS);
        if (realmAccess instanceof Map) {
            Object realmRoles = ((Map<?, ?>) realmAccess).get("roles");
            if (realmRoles instanceof Collection) {
                ((Collection<?>) realmRoles).forEach(r -> {
                    if (r != null) roles.add(String.valueOf(r));
                });
            }
        }

        // 2) client roles (resource_access.{clientId}.roles or all clients)
        Object resourceAccess = jwt.getClaim(RESOURCE_ACCESS);
        if (resourceAccess instanceof Map) {
            Map<?, ?> ra = (Map<?, ?>) resourceAccess;
            if (clientId != null) {
                Object client = ra.get(clientId);
                if (client instanceof Map) {
                    Object clientRoles = ((Map<?, ?>) client).get("roles");
                    if (clientRoles instanceof Collection) {
                        ((Collection<?>) clientRoles).forEach(r -> {
                            if (r != null) roles.add(String.valueOf(r));
                        });
                    }
                }
            } else {
                // add all client roles across clients
                for (Object clientObj : ra.values()) {
                    if (clientObj instanceof Map) {
                        Object clientRoles = ((Map<?, ?>) clientObj).get("roles");
                        if (clientRoles instanceof Collection) {
                            ((Collection<?>) clientRoles).forEach(r -> {
                                if (r != null) roles.add(String.valueOf(r));
                            });
                        }
                    }
                }
            }
        }

        // Map roles to GrantedAuthority with prefix ROLE_ and uppercase
        return roles.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .map(r -> "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
