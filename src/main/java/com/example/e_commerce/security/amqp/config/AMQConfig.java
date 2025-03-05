package com.example.e_commerce.security.amqp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQConfig {

      @Bean
      public Jackson2JsonMessageConverter messageConverter() {
            return new Jackson2JsonMessageConverter(new ObjectMapper());
      }
}
