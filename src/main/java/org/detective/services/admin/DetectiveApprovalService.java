package org.detective.services.admin;

import org.detective.dto.DetectiveApprovalDTO;
import org.detective.entity.DetectiveApproval;
import org.detective.repository.DetectiveApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetectiveApprovalService {

    @Autowired
    private DetectiveApprovalRepository detectiveApprovalRepository;

    public List<DetectiveApprovalDTO> findAll() {
        List<DetectiveApproval> approvals = detectiveApprovalRepository.findAll();
        return approvals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DetectiveApprovalDTO convertToDTO(DetectiveApproval approval) {
        return new DetectiveApprovalDTO(
                approval.getId(),
                approval.getDetective() != null ? approval.getDetective().getDetectiveId() : null,
                approval.getApprovalStatus() != null ? approval.getApprovalStatus().name() : null,
                approval.getRejReason(),
                approval.getCreateAt(),
                approval.getConfirmedAt()
        );
    }


    // 엔티티 저장 로직
    public DetectiveApproval save(DetectiveApproval detectiveApproval) {
        return detectiveApprovalRepository.save(detectiveApproval);
    }

}
