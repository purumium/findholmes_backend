package org.detective.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
public class Client {

    @Id
    @Column(name = "client_id")
<<<<<<< HEAD
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("0")
    private int point;

    @OneToMany(mappedBy ="client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Request> requestList = new ArrayList<>();

    @OneToMany(mappedBy ="client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList = new ArrayList<>();

}
=======
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle에서 IDENTITY 전략을 사용
    private Long clientId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;  // 외래키 설정: User 엔티티의 userId 필드와 매핑


    @ColumnDefault("0")
    @Column(name="currentPoints")
    private Long currentPoints;
}
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
