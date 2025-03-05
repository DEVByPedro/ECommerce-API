package com.example.e_commerce.ecommerce.infra.finan.payment.cntc.cntc_dto;

import com.example.e_commerce.ecommerce.infra.finan.payment.cntc.cntc_enums.CntcSituation;
import jakarta.validation.Valid;

public record CntcDTO(@Valid String name,
                                         @Valid CntcSituation situation) {
}
