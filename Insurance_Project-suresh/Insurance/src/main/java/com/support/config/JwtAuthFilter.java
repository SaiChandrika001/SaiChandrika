package com.support.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.support.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

	private final JwtUtil jwtUtil;

	// Use constructor injection
	public JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1. Extract Authorization Header
		String authHeader = request.getHeader("Authorization");

		// 2. Check for Token Presence and Format
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// No token present or invalid format (e.g., for /register), proceed normally.
			filterChain.doFilter(request, response);
			return;
		}

		// 3. Extract the actual token string
		String token = authHeader.substring(7);

		try {
			// 4. Validate Token and Set Authentication
			if (jwtUtil.validateToken(token)) {
				SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));
			} else {
				logger.warn("JWT token is invalid or expired.");
			}
		} catch (Exception e) {
			// Catch all exceptions during JWT processing (e.g., signature fails, claims missing)
			logger.error("Error processing JWT token: {}", e.getMessage());
			// Do NOT throw an exception here; let the request proceed to the final SecurityFilterChain evaluation
			// This prevents the JwtFilter from prematurely blocking the chain.
		}

		filterChain.doFilter(request, response);
	}
}
