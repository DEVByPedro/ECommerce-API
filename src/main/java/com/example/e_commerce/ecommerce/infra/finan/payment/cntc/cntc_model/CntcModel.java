package com.example.e_commerce.ecommerce.infra.finan.payment.cntc.cntc_model;

import com.example.e_commerce.ecommerce.infra.finan.payment.cntc.cntc_enums.CntcSituation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ContaCorrente")
@Getter
@Setter
public class CntcModel {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Long cntcId;
      private String cntcName;
      private CntcSituation situation;

}
