package org.detective.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Detectives")
public class Detective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detective_id")
    private Long detectiveId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "current_points")
    private Double currentPoints;

    @Column(name = "business_registration")
    private String businessRegistration;

    @Column(name = "detective_license")
    private String detectiveLicense;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Lob
    @Column(name = "introduction")
    private String introduction;

    @Column(name = "location")
    private String location;

    @Column(name = "detective_gender")
    private String detectiveGender;

    @Column(name = "resolved_cases")
    private Long resolvedCases;

    @Column(name = "approval_status")
    private String approvalStatus;

    @ManyToMany
    @JoinTable(
            name = "Detective_Specialties",
            joinColumns = @JoinColumn(name = "detective_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties = new HashSet<>();

    // Getters and setters
}
