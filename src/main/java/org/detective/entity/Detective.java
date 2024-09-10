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
public class Detective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detective_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "business_registration", nullable = false)
    private String bizReg;

    @Column(name = "detective_license", nullable = false)
    private String detLic;

    @Column(name = "profile_picture")
    private String profilePic;

    @Lob
    @Column(name = "introduction")
    private String intro;

    @Column(nullable = false)
    private String location;

    @Column(name = "detective_gender", nullable = false)
    private String gender;

    @Column(name = "additional_certification")
    private String etcCerti;

    @ColumnDefault("0")
    @Column(name = "resolved_count")
    private int solvedCnt;

    @ColumnDefault("0")
    private int point;

    @OneToMany(mappedBy = "detective", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <DetectiveSpecialty> specialties =  new ArrayList<>();

    @OneToMany(mappedBy = "detective", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Estimate> estimates =  new ArrayList<>();

    @OneToMany(mappedBy ="detective", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Review> reviews =  new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private DetectiveApproval detectiveApproval;
=======
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

    @ColumnDefault("0")
    @Column(name="currentPoints")
    private Long currentPoints;

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
>>>>>>> ba05d8b48fd66e75a09124503b943de184bc19bc

}
