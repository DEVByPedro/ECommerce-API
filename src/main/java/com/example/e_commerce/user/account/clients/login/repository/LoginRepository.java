package com.example.e_commerce.user.account.clients.login.repository;

import com.example.e_commerce.user.account.clients.login.model.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, UUID> {
      Optional<LoginModel> findByLogin(String login);
}
