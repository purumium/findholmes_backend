package org.detective.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
import lombok.NoArgsConstructor;
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

=======
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc

@Entity
<<<<<<< HEAD
@Getter @Setter
@Table(name = "Users") // 테이블 이름은 대소문자 구분이 있으므로 정확하게 작성
=======
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS") // 테이블 이름은 대소문자 구분이 있으므로 정확하게 작성
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle에서 IDENTITY 전략을 사용
    private Long Id;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

<<<<<<< HEAD
    @Column(name = "phone_number", nullable = false)
=======
    @Column(name = "phonenumber", nullable = false)
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @CreationTimestamp
<<<<<<< HEAD
    @Column(name = "created_at", updatable = false, nullable = false)
=======
    @Column(name = "created_at", updatable = false, insertable = false)
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
<<<<<<< HEAD

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Detective detective;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Client cilent;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reportList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserPoint> userPointList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> paymentList = new ArrayList<>();

=======
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
}
