package com.example.e_commerce.ecommerce.infra.finan.payment.cntc.cntc_repository;

import com.example.e_commerce.ecommerce.infra.finan.payment.cntc.cntc_model.CntcModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CntcRepository extends JpaRepository<CntcModel, Long> {
}
