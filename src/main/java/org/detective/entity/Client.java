package org.detective.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "Clients") // 테이블 이름은 대소문자 구분이 있으므로 정확하게 작성
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENTS") // 테이블 이름은 대소문자 구분이 있으므로 정확하게 작성
>>>>>>> jpa
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle에서 IDENTITY 전략을 사용
    private Long clientId;

<<<<<<< HEAD
    @Column(name = "user_id", nullable = false)
    private Long userId;

//    @Column(name = "current_points", precision = 10, scale = 2)
//    private Double currentPoints = 0.0;

    // BigDecimal 사용
    @Column(name = "current_points", precision = 10, scale = 2, nullable = false)
    private BigDecimal currentPoints = BigDecimal.valueOf(0.00);

    // 기본 생성자
    public Client() {}

    // 생성자
    public Client(Long clientId, Long userId, BigDecimal currentPoints) {
        this.clientId = clientId;
        this.userId = userId;
        this.currentPoints = currentPoints;
    }
=======
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;  // 외래키 설정: User 엔티티의 userId 필드와 매핑


    @ColumnDefault("0")
    @Column(name="currentPoints")
    private Long currentPoints;
>>>>>>> jpa
}
