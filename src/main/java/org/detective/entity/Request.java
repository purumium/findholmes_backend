package org.detective.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.detective.dto.RequestDTO;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Requests")
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;  // Client 엔티티와의 관계

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "detective_gender", length = 10)
    //@Enumerated(EnumType.STRING)  // Enum 타입으로 처리 가능
    private String detectiveGender;

    @ManyToOne
    @JoinColumn(name = "request_type", nullable = false)
    private Specialty specialty;  // Specialty 엔티티와의 관계

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // Getters and Setters
    // Constructor (No-arg and All-arg)
    // @PreUpdate to handle updatedAt field
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public Request(Long clientId, String location, String detectiveGender, Specialty specialty, String description) {
        this.clientId = clientId;
        this.location = location;
        this.specialty = specialty;
        this.detectiveGender = detectiveGender;
        this.description = description;
    }
    // Getters, Setters, Constructors, etc.
}