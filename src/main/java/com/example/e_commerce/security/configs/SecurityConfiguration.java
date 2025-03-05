package com.example.e_commerce.security.configs;

import com.example.e_commerce.user.account.clients.register.roles.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

      @Autowired
      private AuthorizationService authorizationService;

      @Autowired
      private SecurityFilter securityFilter;

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            return httpSecurity
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests( authorize -> authorize
                            .requestMatchers(HttpMethod.GET, "ecommerce/account/getAllUsers").hasRole(UserRole.ADMIN.toString())
                            .requestMatchers(HttpMethod.DELETE, "ecommerce/account/deleteAll").hasRole(UserRole.ADMIN.toString())
                            .requestMatchers(HttpMethod.DELETE, "ecommerce/account/getAllUsers").hasRole(UserRole.ADMIN.toString())

                            .anyRequest().permitAll()
                    )

                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
      }

      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
      }

      @Bean
      public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
      }
}
