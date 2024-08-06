package com.ubio.stockdemo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login**", "/error").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/")
                                .defaultSuccessUrl("/home", true)
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.oidcUserService(oidcUserService())
                                )
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                );
        return http.build();
    }


    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }
}