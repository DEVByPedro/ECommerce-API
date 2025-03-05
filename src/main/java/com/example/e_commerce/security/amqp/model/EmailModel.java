package com.example.e_commerce.security.amqp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmailModel {

      private UUID userId;
      private String emailTo;
      private String subject;
      private String text;

}
