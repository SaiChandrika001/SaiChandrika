package com.support.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtUtil {

	// Generate a long random 32+ byte key for production
	private static final String SECRET_KEY = "my_very_secret_key_1234567890_change_it_in_prod";

	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

	private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	// ✅ Generate JWT Token
	public String generateToken(String username) {
		return builder()
				.setSubject(username)
				.claim("roles", List.of("USER")) // optional role claim
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(KEY, SignatureAlgorithm.HS256)
				.compact();
	}

	// ✅ Validate Token
	public boolean validateToken(String token) {
		try {
			Claims claims = parseClaims(token);
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	// ✅ Extract Username
	public String extractUsername(String token) {
		return parseClaims(token).getSubject();
	}

	// ✅ Get Authentication Object for Spring Security
	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);
		String username = claims.getSubject();

		List<String> roles = claims.get("roles", List.class);
		List<SimpleGrantedAuthority> authorities = roles == null ?
				Collections.emptyList() :
				roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}

	// ✅ Parse Claims using new parserBuilder()
	private Claims parseClaims(String token) {
		return parser()
				.setSigningKey(KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
