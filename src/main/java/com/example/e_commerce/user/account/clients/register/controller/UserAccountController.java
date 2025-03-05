package com.example.e_commerce.user.account.clients.register.controller;

import com.example.e_commerce.user.account.clients.register.exceptions.InvalidCPF;
import com.example.e_commerce.user.account.clients.register.exceptions.UpdateDataException;
import com.example.e_commerce.user.account.clients.register.exceptions.UserAlreadyExists;
import com.example.e_commerce.user.account.clients.register.exceptions.UserNotFound;
import com.example.e_commerce.user.account.clients.login.dto.LoginDTO;
import com.example.e_commerce.user.account.clients.login.services.LoginServices;
import com.example.e_commerce.user.account.clients.register.dto.RegisterDTO;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import com.example.e_commerce.user.account.clients.register.service.UserAccountServices;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Used to control data, sets the URLs of API's application,
 * Sets the 'way' of data, sending them to its respective services,
 * depending on its models.
 */
@RestController
@RequestMapping("ecommerce/account")
public class UserAccountController {

      @Autowired
      private PasswordEncoder passwordEncoder;

      @Autowired
      private UserAccountServices accountServices;

      @Autowired
      private LoginServices loginServices;

      @PostMapping("/register")
      public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterDTO data) {
            return accountServices.findByUsername(data.username()).<ResponseEntity<Object>>
                    map(model -> ResponseEntity.badRequest().body(new UserAlreadyExists(data.username()).toString()))
                    .orElseGet(() -> validateData(data));
      }

      @PostMapping("/login")
      public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO data) throws Exception{
            return loginServices.canLogIn(data) ?
                    ResponseEntity.ok(loginServices.publishTwoStepCodes(data)) :
                    ResponseEntity.badRequest().body(new UserNotFound().getMessage());
      }

      @PutMapping("/update={user}")
      public ResponseEntity<Object> updateUser(@Valid @PathVariable(value = "user") String user,  @Valid @RequestBody RegisterDTO data) {
            return accountServices.findByUsername(user).<ResponseEntity<Object>>
                    map(account -> ResponseEntity.ok(updateData(data)))
                    .orElseGet(() -> ResponseEntity.badRequest().body(new UserNotFound().getMessage()));
      }

      @GetMapping("/getUser={cpf}")
      public ResponseEntity<Object> findCpf( @Valid @PathVariable(value = "cpf") String cpf) {
            return loginServices.findByCpf(cpf) != null ?
                    ResponseEntity.ok(loginServices.findByCpf(cpf))
                    : ResponseEntity.badRequest().body(new UserNotFound().getMessage());
      }

      @GetMapping("/getAllUsers")
      public ResponseEntity<List<RegisterModel>> getAll() {
            return ResponseEntity.ok(accountServices.findAll());
      }

      @DeleteMapping("/deleteAll")
      public ResponseEntity<Object> deleteAll() {
            return ResponseEntity.ok(accountServices.deleteAll());
      }

      public RegisterModel updateData(RegisterDTO data) {
            RegisterModel model = loginServices.findByCpf(data.userCpf());

            BeanUtils.copyProperties(data, model);
            model.setPassword(passwordEncoder.encode(model.getPassword()));
            model.setUserCpf(model.getUserCpf());

            return accountServices.save(model);
      }

      public ResponseEntity<Object> validateData(RegisterDTO data) {

            if( loginServices.findByCpf(data.userCpf()) != null)
                  return ResponseEntity.badRequest().body(new InvalidCPF().getMessage());

            String year = data.birthDate().substring(0, 4);
            String month = data.birthDate().substring(5,7);
            String day = data.birthDate().substring(8, data.birthDate().length());

            int userAge = LocalDateTime.now().getYear() - Integer.parseInt(year);
            if (userAge >= 17
                    && LocalDateTime.now().getMonthValue() <= Integer.parseInt(month)
                    && LocalDateTime.now().getDayOfMonth() <= Integer.parseInt(day)) {
                  userAge--;
                  if(userAge >= 17)
                        if (accountServices.validateCpf(data)) {
                              RegisterModel model = new RegisterModel();
                              BeanUtils.copyProperties(data, model);
                              model.setPassword(passwordEncoder.encode(model.getPassword()));
                              model.setUserCpf(passwordEncoder.encode(model.getUserCpf()));

                              return ResponseEntity.ok(accountServices.save(model));
                        }

                  return ResponseEntity.badRequest().body(new InvalidCPF().getMessage());
            }

            return ResponseEntity.badRequest().body("You are not allowed.");
      }


}
