package com.localchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                // Disable CSRF completely — SockJS sends XHR polling
                // requests that don't carry CSRF tokens, so Spring
                // was returning 403 on every xhr_streaming and xhr_send
                .disable()
            )
            .authorizeHttpRequests(auth -> auth
                // Explicitly permit the WebSocket handshake endpoint
                .requestMatchers("/ws/**").permitAll()
                // Permit SockJS info endpoint
                .requestMatchers("/ws/info").permitAll()
                // Permit REST endpoints
                .requestMatchers("/api/**").permitAll()
                // Permit everything else
                .anyRequest().permitAll()
            )
            // Disable the default Spring Security login page
            // Without this Spring redirects unauthenticated requests
            // to /login which breaks the WebSocket upgrade
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}