package org.detective.dto;

import lombok.Data;

import java.util.List;

@Data
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
    private String additionalCertifications;
    private String approvalStatus;
    private List<Long> specialties; // 전문 분야 ID 배열

    // Getters and setters
}
