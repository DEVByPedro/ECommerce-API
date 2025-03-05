package com.example.e_commerce.user.account.clients.register.model;

import com.example.e_commerce.user.account.clients.register.roles.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "register_user")
@Getter
@Setter
public class RegisterModel implements Serializable {

      /**
       * The user account default model, all user data management  gonna be based
       * on this, used as a template to save, update and change user data.
       */
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      @Column(unique = true)
      private UUID userId;

      @Column(unique = true, nullable = false)
      @NotNull
      private String username;

      @NotNull(message = "The personal name can not be null")
      private String fullname;

      @Column(nullable = false)
      @Size(min = 8, message = "The password should have a length of 8 chars or higher.")
      private String password;

      @Column(unique = true, nullable = false)
      @Size(min = 11, message = "Cpf is not complete.")
      private String userCpf;

      @Column(unique = true, nullable = false)
      private String email;

      @Column(nullable = false)
      private UserRole role;

      @Size(min = 8, message = "Please, insert a valid date (YYYY-MM-DD)")
      private String birthDate;
}
