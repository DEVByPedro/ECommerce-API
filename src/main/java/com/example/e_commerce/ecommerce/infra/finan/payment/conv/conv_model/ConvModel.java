package com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_model;

import com.example.e_commerce.ecommerce.infra.finan.payment.cntc.cntc_model.CntcModel;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.bill_type.BillType;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.cnabRem.CnabType;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.moneyModel.MoneyType;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.situation.ConvSituation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Convenios")
@Getter
@Setter
public class ConvModel {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Double convId;

      @NotEmpty
      @Size(min = 10, message = "Bank ID should be two-digits long!")
      private Integer bankID;

      @NotEmpty(message = "Current Account Should NOT be null!")
      private String convCNTC;

      @NotEmpty(message = "The AGENCY of this bank Should NOT be null!")
      private Long agency;

      @NotNull(message = "The situation can't be null")
      private ConvSituation situation;
      private String complement;

      @NotNull(message = "Please, insert the money type of your country.")
      private MoneyType moneyType;

      @NotNull(message = "Please, inform us the allowed CNAB for REM.")
      private CnabType cnabForRem;

      @NotNull(message = "Please, inform us the allowed CNAB for RET.")
      private CnabType cnabForRet;

      @NotNull(message = "Please, inform us the allowed Bill Type.")
      private BillType billType;

      @NotNull(message = "Please, inform us with the convenium has PIX.")
      private boolean hasPix;

}