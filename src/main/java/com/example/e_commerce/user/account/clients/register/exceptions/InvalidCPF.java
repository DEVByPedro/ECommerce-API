package com.example.e_commerce.user.account.clients.register.exceptions;

public class InvalidCPF extends RuntimeException {
      public InvalidCPF() {
            super("Occured an error, maybe the cpf is incorrect?");
      }
}
