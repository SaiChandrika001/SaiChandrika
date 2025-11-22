package com.notification.controller;

import java.util.Map;

import com.notification.binding.AuthRequest;
import com.notification.binding.AuthResponse;
import com.notification.entity.UserEntity;
import com.notification.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.config.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest authRequest) {


		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		String token=jwtUtil.generateToken(authRequest.getUserName());
		UserEntity user = userRepository.findByUserName(authRequest.getUserName()).orElseThrow();
		user.setToken(token);
		userRepository.save(user);
		return new AuthResponse(token);
	}


	@PostMapping("/register")
	public String register(@RequestBody AuthRequest request){

		UserEntity user=new UserEntity();
		user.setUserName(request.getUserName());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userRepository.save(user);
		return "User registered successfully";
	}
}
