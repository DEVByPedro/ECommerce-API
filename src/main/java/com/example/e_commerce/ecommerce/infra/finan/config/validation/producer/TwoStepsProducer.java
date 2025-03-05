package com.example.e_commerce.ecommerce.infra.finan.config.validation.producer;

import com.example.e_commerce.security.amqp.model.EmailModel;
import com.example.e_commerce.ecommerce.infra.finan.config.validation.services.CodeService;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import com.example.e_commerce.user.account.clients.register.service.UserAccountServices;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwoStepsProducer {

      @Autowired
      private UserAccountServices accountServices;

      @Autowired
      private CodeService codeService;

      private final RabbitTemplate rabbitTemplate;

      public TwoStepsProducer(RabbitTemplate rabbitTemplate) {
            this.rabbitTemplate = rabbitTemplate;
      }

      @Value("${broker.queue.email.name}")
      private String routingKey;

      public String publishTwoStepsCode(RegisterModel model) {

            String verificationCode = codeService.generateCode(model);

            EmailModel emailModel = new EmailModel();
            emailModel.setUserId(model.getUserId());
            emailModel.setEmailTo(model.getEmail());
            emailModel.setSubject("You Two-Steps Verification Has Arrived!");
            emailModel.setText(model.getUsername() + ", Here is your Two-Steps Verification Code: " + verificationCode.toString());

            rabbitTemplate.convertAndSend("", routingKey, emailModel);
            return verificationCode;
      }
}
