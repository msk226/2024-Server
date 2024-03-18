package com.example.demo.src.payment;

import com.example.demo.src.payment.entity.Subscribe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long>,
    JpaSpecificationExecutor<Subscribe> {

   Optional<Subscribe> findByUserIdAndPaymentId(Long userId, Long paymentId);
   boolean existsByUserIdAndPaymentId(Long userId, Long paymentId);
}
