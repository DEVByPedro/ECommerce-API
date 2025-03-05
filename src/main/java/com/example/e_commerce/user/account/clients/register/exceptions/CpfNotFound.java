package com.example.e_commerce.user.account.clients.register.exceptions;

public class CpfNotFound extends RuntimeException {
      public CpfNotFound() {
            super("No CPF found.");
      }
}
