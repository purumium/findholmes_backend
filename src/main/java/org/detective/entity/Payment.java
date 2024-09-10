package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_uuid", nullable = false)
    private String impUid;

    @Column(name = "merchant_uuid", nullable = false)
    private String merchantUid;  // 결제 고유 번호 (UUID 등으로 생성된 결제 식별자)

    @ManyToOne  // 사용자가 여러 개의 결제 건을 만들 수 있음
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user; // 외래키 설정: User 엔티티의 userId 필드와 매핑

    @Column(name = "price")
    private Long price;  // 결제 금액

    @Column(name = "email")
    private String email;

    @Column(name = "payment_details")
    private String paymentDetails;  // 결제 내역

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;  // 결제 방법

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "payment_at", nullable = false, updatable = false)
    private String paymentAt;

    // 결제 상태
    public enum PaymentStatus {
        COMPLETED, FAILED, CANCEL
    }

    // 결제 방법
    public enum PaymentMethod {
        credit_card, other
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();  // 현재 날짜와 시간
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        String formattedDate = now.format(formatter);

        this.paymentAt = formattedDate;
    }

}