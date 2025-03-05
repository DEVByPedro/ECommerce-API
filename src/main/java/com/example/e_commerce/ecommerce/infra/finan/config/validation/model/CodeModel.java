package com.example.e_commerce.ecommerce.infra.finan.config.validation.model;

import com.example.e_commerce.ecommerce.infra.finan.config.validation.code_enums.CodeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.UUID;

@Entity
@Table(name = "code_table")
@Getter
@Setter
public class CodeModel {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private UUID codeId;

      @NotNull(message = "Username Could not be null.")
      private String username;

      @Column(unique = true, nullable = false)
      private String userCode;
      private CodeStatus codeStatus;

      @NotNull(message = "Generation Time could not be null.")
      private String generationTime;

}
