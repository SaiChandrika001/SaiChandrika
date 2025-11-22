package com.support.service;

import com.support.binding.AuthRequest;
import com.support.binding.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {

     AuthResponse login(AuthRequest request);

     String register(AuthRequest request);

     UserDetails loadUserByUsername(String username);


}
