package com.wink.gongongu.domain.payment.repository;

import com.wink.gongongu.domain.payment.entity.Payment;
import com.wink.gongongu.domain.payment.entity.PaymentStatus;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentIdAndUserId(Long paymentId, Long userId);

    Slice<Payment> findByUserIdOrderByPaymentIdDesc(Long userId, Pageable pageable);

    Slice<Payment> findByUserIdAndStatusOrderByPaymentIdDesc(Long userId, PaymentStatus status, Pageable pageable);
}
