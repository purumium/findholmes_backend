package org.detective.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DETECTIVES")
public class Detective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detective_id")
    private Long detectiveId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name="currentPoints", nullable = false)
    private Long currentPoints = 0L;

    @Column(name = "business_registration")
    private String businessRegistration;

    @Column(name = "detective_license")
    private String detectiveLicense;


    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "company")
    private String company;

    @Column(name = "additional_certifications")
    private String additionalCertifications;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "introduction")
    private String introduction;

    @Column(name = "location")
    private String location;

    @Column(name = "detective_gender")
    private String detectiveGender;

    @Column(name = "resolved_cases")
    private Long resolvedCases;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    @OneToMany(mappedBy = "detective", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetectiveSpeciality> specialties =  new ArrayList<>();

}
