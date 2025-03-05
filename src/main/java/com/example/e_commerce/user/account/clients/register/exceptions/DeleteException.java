package com.example.e_commerce.user.account.clients.register.exceptions;

public class DeleteException extends RuntimeException {
      public DeleteException() {
            super("Could not delete user's data.");
      }
}
