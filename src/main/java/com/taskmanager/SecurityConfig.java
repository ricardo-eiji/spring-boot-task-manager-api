package com.taskmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SecurityConfig
{
    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;
    // then: .password(encoder.encode(adminPassword))

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    // PasswordEncoder — passwords are never stored in plain text, even in-memory

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder)
    {
        UserDetails user = User.builder()
            .username("admin")
            .password(encoder.encode(adminPassword))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
    // InMemoryUserDetailsManager — defines a fixed user (admin / admin123) 
        // instead of an auto-generated password

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .httpBasic(basic -> {});
        return http.build();
    }
    // SecurityFilterChain — defines the rule: every request must be authenticated, 
        // using basic auth

}