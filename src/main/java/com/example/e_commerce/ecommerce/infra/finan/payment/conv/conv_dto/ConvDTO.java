package com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_dto;

import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.bill_type.BillType;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.cnabRem.CnabType;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.moneyModel.MoneyType;
import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_enums.situation.ConvSituation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ConvDTO(@Valid @NotNull Integer bankID,
                                          @Valid @NotNull Long convCNTC,
                                          @Valid @NotNull Long agency,
                                          @Valid @NotNull ConvSituation situation,
                                          @Valid @NotNull String complement,
                                          @Valid @NotNull MoneyType moneyType,
                                          @Valid @NotNull CnabType cnabForRem,
                                          @Valid @NotNull CnabType cnabForRet,
                                          @Valid @NotNull BillType billType,
                                          @Valid @NotNull boolean hasPix){
}
