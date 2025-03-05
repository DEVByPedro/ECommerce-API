package com.example.e_commerce.ecommerce.infra.finan.config.validation.repository;

import com.example.e_commerce.ecommerce.infra.finan.config.validation.model.CodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends JpaRepository<CodeModel, UUID> {
      Optional<CodeModel> findByUserCode(String userCode);
      Optional<CodeModel> findByUsername(String username);
}
