package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectiveDTO {
    private Long userId;
    private Double currentPoints;
    private String businessRegistration;
    private String detectiveLicense;
    private String profilePicture;
    private String introduction;
    private String location;
    private String detectiveGender;
    private Long resolvedCases;
    private String approvalStatus;
    private List<Long> specialties; // 전문 분야 ID 배열
    private String password;

    private String company;
    private String additionalCertifications;

    private String description;

    private List<String> specialtiesName;

    //user 정보
    private String userName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;

    // Getters and setters
}