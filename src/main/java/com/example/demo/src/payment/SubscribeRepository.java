package com.example.demo.src.payment;

import com.example.demo.src.payment.entity.Subscribe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

   Optional<Subscribe> findByUserIdAndPaymentId(Long userId, Long paymentId);
   boolean existsByUserIdAndPaymentId(Long userId, Long paymentId);
}
