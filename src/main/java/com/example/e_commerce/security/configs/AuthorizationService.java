package com.example.e_commerce.security.configs;

import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import com.example.e_commerce.user.account.clients.register.service.UserAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorizationService  implements UserDetailsService {

      @Autowired
      private UserAccountServices accountServices;

      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            RegisterModel model = accountServices.findByUsername(username).get();

            return new User(
                    model.getUsername(),
                    model.getPassword(),
                    new ArrayList<>()
            );
      }
}
