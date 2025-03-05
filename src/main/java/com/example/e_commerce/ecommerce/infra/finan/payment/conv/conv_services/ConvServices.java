package com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_services;

import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_configs_validation.ConvValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvServices {

      @Autowired
      private ConvValidationsService convValidation;

}