package com.example.e_commerce.user.account.clients.login.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "login_users")
@Getter
@Setter
public class LoginModel {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private UUID loginID;

      @Column(nullable = false)
      private String login;

      @Column(nullable = false)
      private String password;

      @Column(nullable = false)
      private LocalDateTime time;

}
