package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;   // 의뢰내용

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Request(Client client, String location, String detectiveGender, Speciality speciality,String title, String description) {
        this.client = client;
        this.location = location;
        this.speciality = speciality;
        this.detectiveGender = detectiveGender;
        this.title = title;
        this.description = description;
    }
}