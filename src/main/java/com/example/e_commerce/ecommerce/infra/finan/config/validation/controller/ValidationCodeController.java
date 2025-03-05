package com.example.e_commerce.ecommerce.infra.finan.config.validation.controller;

import com.example.e_commerce.ecommerce.infra.finan.config.validation.dto.CodeDTO;
import com.example.e_commerce.ecommerce.infra.finan.config.validation.exceptions.CodeExpiredException;
import com.example.e_commerce.ecommerce.infra.finan.config.validation.services.CodeService;
import com.example.e_commerce.user.account.clients.login.services.LoginServices;
import com.example.e_commerce.user.account.clients.register.service.UserAccountServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("ecommerce/validation")
@RestController
public class ValidationCodeController {

      @Autowired
      private CodeService codeService;

      @Autowired
      private UserAccountServices accountServices;

      @Autowired
      private LoginServices loginServices;

      @PostMapping("/validate={user}")
      public ResponseEntity<Object> validateCode(@PathVariable("user") String username, @Valid @RequestBody CodeDTO data) {
            return codeService.isUserAvailable(accountServices.findByUsername(username).get().getUsername(), data.code()) ?
            ResponseEntity.ok(loginServices.isAdmin(accountServices.findByUsername(username).get()))
            : ResponseEntity.badRequest().body(new CodeExpiredException().getMessage());
      }
}
