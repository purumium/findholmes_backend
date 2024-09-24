package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENTS") // 테이블 이름은 대소문자 구분이 있으므로 정확하게 작성
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle에서 IDENTITY 전략을 사용
    private Long clientId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;  // 외래키 설정: User 엔티티의 userId 필드와 매핑

    @ColumnDefault("0")
    @Column(name="currentPoints")
    private Long currentPoints;

    public void usePoints(Long points) {
        if (this.currentPoints < points) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        this.currentPoints -= points;
    }
}

