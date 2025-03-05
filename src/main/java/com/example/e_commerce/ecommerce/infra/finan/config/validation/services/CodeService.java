package com.example.e_commerce.ecommerce.infra.finan.config.validation.services;

import com.example.e_commerce.ecommerce.infra.finan.config.validation.code_enums.CodeStatus;
import com.example.e_commerce.ecommerce.infra.finan.config.validation.exceptions.CodeCooldownGenerationException;
import com.example.e_commerce.ecommerce.infra.finan.config.validation.model.CodeModel;
import com.example.e_commerce.ecommerce.infra.finan.config.validation.repository.CodeRepository;
import com.example.e_commerce.user.account.clients.register.exceptions.UserNotFound;
import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import com.example.e_commerce.user.account.clients.register.service.UserAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CodeService {

      @Autowired
      private PasswordEncoder passwordEncoder;

      @Autowired
      private CodeRepository repository;

      @Autowired
      private UserAccountServices accountServices;

      public List<CodeModel> findAllByUser(RegisterModel model) {
            List<CodeModel> allModels = repository.findAll();

            List<CodeModel> myModels = new ArrayList<CodeModel>();
            for (CodeModel codeModel : allModels) {
                  if (codeModel.getUserCode().equals(model.getUsername())) {
                        myModels.add(codeModel);
                  }
            }

            return myModels;
      }

      public String generateCode(RegisterModel registerModel) {

            String content = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
            StringBuilder verificationCode = new StringBuilder();
            for (int i = 0; i < 19; i++) {
                  Random random = new Random();
                  verificationCode.append(content.charAt(random.nextInt(0, content.length())));
            }

            verificationCode.setCharAt(4, '-');
            verificationCode.setCharAt(9, '-');
            verificationCode.setCharAt(14, '-');

            int hour = LocalDateTime.now().getHour();
            int minute = LocalDateTime.now().getMinute();
            int second = LocalDateTime.now().getSecond();

            CodeModel code = new CodeModel();
            code.setUsername(registerModel.getUsername());
            code.setUserCode(passwordEncoder.encode(verificationCode.toString()));
            code.setGenerationTime(hour + ":" + minute + ":" + second);
            code.setCodeStatus(CodeStatus.SENT);
            repository.save(code);

            return verificationCode.toString();
      }

      public boolean isUserAvailable(String username, String code) {

            CodeModel codeModel = repository.findByUsername(username).get();

            List<CodeModel> allCodes = repository.findAll();
            for (CodeModel codeIn : allCodes) {
                  if(codeIn.getUsername().equals(username)) {
                        repository.delete(codeIn);
                  }
            }

            if (passwordEncoder.matches(code, codeModel.getUserCode()) && codeModel.getUsername().equals(username)) {
                  repository.delete(codeModel);
                  return true;
            }

            return false;
      }

}
