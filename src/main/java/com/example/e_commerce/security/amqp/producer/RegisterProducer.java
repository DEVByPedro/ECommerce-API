package com.example.e_commerce.security.amqp.producer;

import com.example.e_commerce.security.amqp.model.EmailModel;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RegisterProducer {

      private final RabbitTemplate rabbitTemplate;

      public RegisterProducer(RabbitTemplate rabbitTemplate) {
            this.rabbitTemplate = rabbitTemplate;
      }

      @Value("${broker.queue.email.name}")
      private String routingKey;

      public void publishRegistrationEmail(RegisterModel model) {
            EmailModel emailModel = new EmailModel();
            emailModel.setUserId(model.getUserId());
            emailModel.setEmailTo(model.getEmail());
            emailModel.setSubject("Welcome to our E-Commerce, "+ model.getUsername()+"!");
            emailModel.setText(model.getUsername()+", your account has been created!\nFeel free to use our app and receive your stuff in your house!");

            rabbitTemplate.convertAndSend("", routingKey, emailModel);
      }

}
