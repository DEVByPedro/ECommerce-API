package com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_controller;

import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_dto.ConvDTO;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_services.ConvServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("convenium")
@RestController
public class ConvController {

      @Autowired
      private ConvServices convServices;

      @PostMapping("/cadConv")
      public ResponseEntity<Object> cadConvenium(@Valid @RequestBody ConvDTO data) {
            return ResponseEntity.ok().build();
      }
}
