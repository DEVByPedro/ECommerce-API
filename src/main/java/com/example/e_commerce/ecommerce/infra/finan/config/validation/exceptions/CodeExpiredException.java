package com.example.e_commerce.ecommerce.infra.finan.config.validation.exceptions;

public class CodeExpiredException extends RuntimeException {
      public CodeExpiredException() {
            super("Code is wrong or maybe expired.");
      }
}
