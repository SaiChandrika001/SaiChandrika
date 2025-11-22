package com.support.service;

import com.support.entity.UserEntity;
import com.support.repo.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// Implements Spring Security's UserDetailsService interface
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Core method for Spring Security to load user-specific data during authentication.
     * @param username The username provided during login.
     * @return UserDetails object containing the user's information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user entity from the database
        UserEntity entity = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return a Spring Security UserDetails object
        return User.builder()
                .username(entity.getUserName())
                .password(entity.getPassword())
                .roles("USER") // Assign a default role
                .build();
    }
}
