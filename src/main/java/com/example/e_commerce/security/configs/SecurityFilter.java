package com.example.e_commerce.security.configs;

import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import com.example.e_commerce.user.account.clients.register.service.UserAccountServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

      @Autowired
      private UserAccountServices accountServices;

      @Autowired
      private TokenService tokenService;

      @Override
      protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {

            var token = recoverToken(request);
            var login = tokenService.validateToken(token);

            if (login != null) {
                  RegisterModel model = accountServices.findByUsername(login)
                          .orElseThrow(() -> new RuntimeException("Error: User not found or the key is duplicated"));

                  var authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_"+model.getRole().toString()));
                  var authentication = new UsernamePasswordAuthenticationToken(model, null, authorities);

                  SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
      }

      private String recoverToken(HttpServletRequest request) {
            var authHeader = request.getHeader("Authorization");
            if(authHeader == null) return null;
            return authHeader.replaceAll("Bearer ", "");
      }
}
