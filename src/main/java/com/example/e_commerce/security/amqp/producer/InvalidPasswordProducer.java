package com.example.e_commerce.security.amqp.producer;

import com.example.e_commerce.security.amqp.model.EmailModel;
import com.example.e_commerce.security.amqp.producer.producerConfig.ProducerConfig;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDateTime;

@Component
public class InvalidPasswordProducer {

      private final RabbitTemplate rabbitTemplate;

      public InvalidPasswordProducer(RabbitTemplate rabbitTemplate) {
            this.rabbitTemplate = rabbitTemplate;
      }

      @Value("${broker.queue.email.name}")
      private String routingKey;

      public void publishLoginTryMessage(RegisterModel model)  {

            String ipAddress = "";
            String publicIp = "";

            try {
                  InetAddress ip = InetAddress.getLocalHost();
                  ipAddress += ip.getHostAddress();

                  URL url = new URL("https://checkip.amazonaws.com");
                  HttpURLConnection Conn = (HttpURLConnection) url.openConnection();
                  Conn.setRequestMethod("GET");

                  BufferedReader reader = new BufferedReader(new InputStreamReader(Conn.getInputStream()));
                  publicIp += reader.readLine();
                  reader.close();

            } catch ( Exception e) {
                  e.printStackTrace();
            }

            ProducerConfig config = new ProducerConfig();
            EmailModel emailModel = new EmailModel();

            emailModel.setUserId(model.getUserId());
            emailModel.setEmailTo(model.getEmail());
            emailModel.setSubject("Someone tried to log in your account!!!");
            emailModel.setText(model.getUsername()+", someone tried to access your account \nAt: "+

                    LocalDateTime.now().getDayOfMonth()+"/"+
                    LocalDateTime.now().getMonthValue()+"/"+
                    LocalDateTime.now().getYear()+

                    "\nIn time: "+

                    LocalDateTime.now().getHour()+":"+
                    LocalDateTime.now().getMinute()+":"+
                    LocalDateTime.now().getSecond()+
                    "\nLocal IP: "        +     ipAddress+
                    "\nPublic IP: "       +     publicIp+
                    "\nLocation: "       +     config.getLocation() +
                    "\nWasn't you? Contact Us."
            );

            rabbitTemplate.convertAndSend("", routingKey, emailModel);
      }

}
