package com.example.e_commerce.user.account.clients.register.exceptions;

public class UserNotFound extends RuntimeException {
      public UserNotFound() {
            super("User not found, maybe invalid credentials?");
      }
}
