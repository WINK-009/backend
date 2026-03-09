package com.wink.gongongu.domain.payment.repository;

import com.wink.gongongu.domain.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentIdAndUserId(Long paymentId, Long userId);
}
