package com.example.e_commerce.security.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

      @Value("${security.token.secret}")
      private String secret;

      public String validateToken(String token) {
            try{
                  Algorithm algorithm = Algorithm.HMAC256(secret);
                  String subject = JWT.require(algorithm)
                          .withIssuer("bank-api")
                          .build()
                          .verify(token)
                          .getSubject();

                  return subject;

            }catch (Exception e) {
                  return null;
            }
      }

      public String generateToken(RegisterModel model) {
            try{

                  Algorithm algorithm = Algorithm.HMAC256(secret);
                  String token = JWT.create()
                          .withIssuer("bank-api")
                          .withSubject(model.getUsername())
                          .withExpiresAt(tokenExpirationTime())
                          .sign(algorithm);

                  return token;

            }catch (JWTCreationException e) {
                  throw new RuntimeException("Error: "+e.getMessage(), e);
            }
      }

      public Instant tokenExpirationTime() {
            return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC);
      }

}