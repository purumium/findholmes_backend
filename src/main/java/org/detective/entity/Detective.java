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
@Table(name = "DETECTIVES")
public class Detective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detective_id")
    private Long detectiveId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name = "currentPoints", nullable = false)
    private Long currentPoints = 0L;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

}