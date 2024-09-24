package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_POINT")
public class UserPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @ManyToOne  // 다수의 포인트 내역이 하나의 사용자와 연결
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;  // 외래키 설정: User 엔티티의 userId 필드와 매핑

    @Enumerated(EnumType.STRING)
    @Column(name = "point_using_type")
    private PointUsingType pointUsingType;  // 'CHARGE', 'USE' 값 저장

    @Column(name = "point_change_amount")
    private Long pointChangeAmount;  // 포인트 변동량

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private String createdAt;


    public enum PointUsingType {
        CHARGE, USE  // 포인트 충전 or 사용
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();  // 현재 날짜와 시간
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        String formattedDate = now.format(formatter);

        this.createdAt = formattedDate;
    }

}