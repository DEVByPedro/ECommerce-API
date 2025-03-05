package com.example.e_commerce.user.account.clients.register.exceptions;

public class UpdateDataException extends RuntimeException {
      public UpdateDataException() {
            super("Could not update user's data, maybe a invalid credential?");
      }
}
