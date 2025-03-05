package com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_repository;

import com.example.e_commerce.ecommerce.infra.finan.payment.conv.conv_model.ConvModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvRepository extends JpaRepository<ConvModel, Double> {
}
