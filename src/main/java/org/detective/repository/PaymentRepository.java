package org.detective.repository;

import org.detective.dto.PaymentResponseDTO;
import org.detective.entity.Payment;
import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  // Payment 엔티티를 위한 JPA 리포지토리 인터페이스
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT new org.detective.dto.PaymentResponseDTO(p.merchantUid, p.paymentDetails, " +
            "p.paymentAt, p.price) " +
            "FROM Payment p where p.user.userId = :userId " +
            "ORDER BY p.paymentAt DESC")
    List<PaymentResponseDTO> getPaymentDetailsByUserId(@Param("userId") Long userId);
}
