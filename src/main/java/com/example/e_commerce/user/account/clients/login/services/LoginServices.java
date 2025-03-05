package com.example.e_commerce.user.account.clients.login.services;

import com.example.e_commerce.ecommerce.infra.finan.config.validation.services.CodeService;
import com.example.e_commerce.security.amqp.producer.InvalidPasswordProducer;
import com.example.e_commerce.ecommerce.infra.finan.config.validation.producer.TwoStepsProducer;
import com.example.e_commerce.security.configs.TokenService;
import com.example.e_commerce.user.account.clients.login.dto.LoginDTO;
import com.example.e_commerce.user.account.clients.login.model.LoginModel;
import com.example.e_commerce.user.account.clients.login.repository.LoginRepository;
import com.example.e_commerce.user.account.clients.register.exceptions.CpfNotFound;
import com.example.e_commerce.user.account.clients.register.exceptions.InvalidCPF;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import com.example.e_commerce.user.account.clients.register.roles.UserRole;
import com.example.e_commerce.user.account.clients.register.service.UserAccountServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServices {

      @Autowired
      private TokenService tokenService;

      @Autowired
      private PasswordEncoder passwordEncoder;

      @Autowired
      private UserAccountServices accountServices;

      @Autowired
      private LoginRepository repository;

      @Autowired
      private TwoStepsProducer codeProducer;

      @Autowired
      private InvalidPasswordProducer invalidPasswordProducer;

      @Autowired
      private CodeService codeService;

      public boolean  canLogIn(LoginDTO data) throws Exception{

            // verifies if user is available
            Optional<RegisterModel> isModelPresent = accountServices.findByUsername(data.login());
            if(!isModelPresent.isPresent()) {
                  System.out.println("No data found");
                  return false;
            }

            // if user is available, gets it
            RegisterModel model = isModelPresent.get();

            // sees if the pass matches
            if (passwordEncoder.matches(data.password(), model.getPassword())) {
                  LoginModel loginModel = new LoginModel();
                  BeanUtils.copyProperties(data, loginModel);
                  loginModel.setTime(LocalDateTime.now());
                  loginModel.setPassword(data.password());
                  repository.save(loginModel);
            } else {
                  invalidPasswordProducer.publishLoginTryMessage(model);

                  return false;
            }
            return true;
      }

      public String publishTwoStepCodes(LoginDTO data) {

            return codeProducer.publishTwoStepsCode(accountServices.findByUsername(data.login()).get());
      }

      public String isAdmin(RegisterModel model) {
            if(model.getRole().equals(UserRole.ADMIN))
                  return tokenService.generateToken(model);
            else
                  return "Welcome, "+model.getUsername()+"!";
      }

      public RegisterModel findByCpf(String cpf) throws InvalidCPF {
            List<RegisterModel> list = accountServices.findAll();

            for (int i = 0; i < list.size(); i++) {
                  if(passwordEncoder.matches(cpf, list.get(i).getUserCpf()))
                        return list.get(i);
            }

            return null;
      }
}