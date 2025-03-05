package com.example.e_commerce.user.account.clients.register.exceptions;

public class UserAlreadyExists extends RuntimeException {
      public UserAlreadyExists(String username) {
            super("The username: \""+username+"\" is already in use!");
      }
}
