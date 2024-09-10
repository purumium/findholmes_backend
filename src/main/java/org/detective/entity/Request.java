package org.detective.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Getter;
import lombok.Setter;
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

<<<<<<< HEAD
@Entity
@Getter
@Setter
public class Request {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;

    private String location;

    @Column(name = "detective_gender")
    private String gender;

    @OneToOne(fetch = FetchType.LAZY)
    @Column(name = "request_type")
    private DetectiveSpecialty category;

    @Lob
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
=======
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REQUESTS")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
    private Client client;  // Client 엔티티와의 관계

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "detective_gender", length = 10)
    //@Enumerated(EnumType.STRING)  // Enum 타입으로 처리 가능
    private String detectiveGender;

    @ManyToOne
    @JoinColumn(name = "request_type", referencedColumnName = "speciality_id", nullable = false)
    private Speciality speciality;  // Specialty 엔티티와의 관계

    @Lob
    @Column(name = "description")
    private String description;   // 의뢰내용

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

<<<<<<< HEAD
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="estimate_id")
    private Estimate estimate;

}
=======

    public Request(Client client, String location, String detectiveGender, Speciality speciality, String description) {
        this.client = client;
        this.location = location;
        this.speciality = speciality;
        this.detectiveGender = detectiveGender;
        this.description = description;
    }
}
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc
