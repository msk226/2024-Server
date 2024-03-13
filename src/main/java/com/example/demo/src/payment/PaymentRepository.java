package com.example.demo.src.payment;

import com.example.demo.src.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long>{

    boolean existsByImpUidAndMerchantUid(Long impUid, Long merchantUid);

    Payments findByImpUidAndMerchantUid(Long impUid, Long merchantUid);
}
