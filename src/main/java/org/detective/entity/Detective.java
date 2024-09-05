package org.detective.entity;

import jakarta.persistence.*;
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

}
