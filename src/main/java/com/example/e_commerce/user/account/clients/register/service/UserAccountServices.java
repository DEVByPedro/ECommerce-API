package com.example.e_commerce.user.account.clients.register.service;

import com.example.e_commerce.security.amqp.producer.RegisterProducer;
import com.example.e_commerce.user.account.clients.register.dto.RegisterDTO;
import com.example.e_commerce.user.account.clients.register.exceptions.DeleteException;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import com.example.e_commerce.user.account.clients.register.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Used to management of data, also encrypt, decrypt
 * user data.
 */
@Service
public class UserAccountServices {

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private RegisterProducer producer;

      public RegisterModel save(RegisterModel model) {
            producer.publishRegistrationEmail(model);
            return userRepository.save(model);
      }

      public Optional<RegisterModel> findById(UUID id) {
            return userRepository.findById(id);
      }

      public Optional<RegisterModel> findByUsername(String username) {
            return userRepository.findByUsername(username);
      }

      public Optional<RegisterModel> findByFullname(String fullname) {
            return userRepository.findByFullname(fullname);
      }

      public List<RegisterModel> findAll() {
            return userRepository.findAll();
      }

      public List<RegisterModel> findAllByUsername(String username) {
            List<RegisterModel> allUsers = userRepository.findAll();
            List<RegisterModel> finalList = new ArrayList<RegisterModel>();
            for (RegisterModel model : allUsers) {
                  if(model.getUsername().equals(username))
                        finalList.add(model);
            }

            return finalList;
      }

      public boolean validateCpf(RegisterDTO data) {
            String cpf = data.userCpf().replaceAll("[^0-9]", "");
            int[] numbers = new int[cpf.replaceAll("[^0-9]", "").length()];

            // Validates Cpf:
            int count = 0;
            for (int i = 0; i < cpf.length(); i++)
                  count += Integer.parseInt(String.valueOf(cpf.charAt(i)));

            if((count == 33) || (count == 44) || (count == 55) || (count == 66))
                  return true;
            return false;
      }

      public String deleteAll() {
            int usersDeleted = userRepository.findAll().size();
            try {
                  userRepository.deleteAll();
            } catch (Exception e) {
                  return new DeleteException().getMessage();
            }

            return "All " + usersDeleted + " users has been deleted";
      }

}
