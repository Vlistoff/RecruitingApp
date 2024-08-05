package com.vlistoff.recruiting.config;

import com.vlistoff.recruiting.config.security.CustomAuthenticationSuccessHandler;
import com.vlistoff.recruiting.config.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class AppConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/jobs/list", "/jobs/view/**", "/jobs/search").permitAll()  // Разрешить доступ без аутентификации
                        .requestMatchers("/jobs/create", "/jobs/myjobs", "/jobs/edit/**", "/applications/manageApplication").hasAuthority("RECRUITER")
                        .requestMatchers("/jobs/list", "/applications/my").hasAuthority("CANDIDATE")
                        .anyRequest().authenticated()  // Требовать аутентификацию для всех остальных запросов
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")  // Страница входа
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL для инициации выхода
                        .logoutSuccessUrl("/auth/login")  // Куда перенаправлять после выхода
                        .permitAll()  // Разрешить доступ к выходу всем
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, CustomUserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
