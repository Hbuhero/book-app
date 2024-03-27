package com.book.management.Configuration;

import com.book.management.Configuration.CustomAuthenticationSuccessHandler;
import com.book.management.Model.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
    private final String[] openUrls = {
            "/api/v1/user/register",
            "/api/v1/publisher/register",
            "/login",
            "/signup/customer",
            "/signup/publisher"
    };

    private final String [] publisherUrls = {
            "/api/v1/publisher/update",
            "/api/v1/publisher/{id}/books",
            "/api/v1/files/upload-file",
            "/api/v1/files/delete/{fileid}"
    };

    private final String[] customerUrl = {
            "/api/v1/user/change-password",
            "/api/v1/user/update-profile/{id}"
    };

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/signup", "/signup-customer").permitAll()
                        .requestMatchers(openUrls).permitAll()
                        .requestMatchers(publisherUrls).hasRole(Roles.PUBLISHER.name())
                        .requestMatchers(customerUrl).hasRole(Roles.CUSTOMER.name())
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(login -> login.successHandler(authenticationSuccessHandler()));

        return httpSecurity.build();

    }
}
