package com.example.e_commerce.ecommerce.infra.finan.config.validation.exceptions;

public class CodeCooldownGenerationException extends RuntimeException {
      public CodeCooldownGenerationException() {
            super("Cooldown on, please try again in one minute.");
      }
}
