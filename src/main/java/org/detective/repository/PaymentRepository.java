package org.detective.repository;

import org.detective.dto.PaymentResponseDTO;
import org.detective.dto.PaymentTotalDTO;
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

    //날짜별 결제 내역
    @Query("SELECT new org.detective.dto.PaymentTotalDTO(" +
            "SUBSTRING(p.paymentAt, 1, 10), " +  // paymentAt의 앞 10자리(yyyy-MM-dd)만 추출
            "SUM(CASE WHEN p.user.role = 'ROLE_USER' THEN p.price ELSE 0 END), " +
            "SUM(CASE WHEN p.user.role = 'ROLE_DETECTIVE' THEN p.price ELSE 0 END)) " +
            "FROM Payment p " +
            "GROUP BY SUBSTRING(p.paymentAt, 1, 10) " +
            "ORDER BY SUBSTRING(p.paymentAt, 1, 10)")
    List<PaymentTotalDTO> getTotalPriceByRolesAndDate();
}
