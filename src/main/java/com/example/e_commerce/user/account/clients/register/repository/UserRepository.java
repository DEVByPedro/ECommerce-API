package com.example.e_commerce.user.account.clients.register.repository;

import com.example.e_commerce.user.account.clients.register.model.RegisterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Used to save, transfer and show data from database,
 * needs to have safety information.
 */
@Repository
public interface UserRepository extends JpaRepository<RegisterModel, UUID> {
      Optional<RegisterModel> findByUserCpf(String userCpf);
      Optional<RegisterModel> findByUsername(String username);
      Optional<RegisterModel> findByFullname(String fullname);
}
