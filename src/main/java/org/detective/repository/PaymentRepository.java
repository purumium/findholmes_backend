package org.detective.repository;

import org.detective.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // Payment 엔티티를 위한 JPA 리포지토리 인터페이스
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
