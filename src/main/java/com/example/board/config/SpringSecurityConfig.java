package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf ->
                        csrf.disable()
                ).cors(cors ->
                        cors.disable()
                )
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/boards/**").permitAll()
                                .requestMatchers("/boards/{id}").authenticated()
                                .requestMatchers("/user/join").permitAll()
                                .requestMatchers("/user/login").permitAll()
                ).formLogin(formLogin -> formLogin.disable()).httpBasic(httpBasic -> httpBasic.disable())
        ;

        return http.build();
    }
}