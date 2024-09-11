package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectiveApprovalDTO {

    private Long id;
    private Long detectiveId;
    private String approvalStatus;
    private String rejReason; // 클립형 텍스트
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;

}
