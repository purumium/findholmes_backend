package org.detective.controller.Admin;

import org.detective.dto.DetectiveApprovalDTO;
import org.detective.entity.ApprovalStatus;
import org.detective.entity.Detective;
import org.detective.entity.DetectiveApproval;
import org.detective.repository.DetectiveApprovalRepository;
import org.detective.repository.DetectiveRepository;
import org.detective.services.admin.DetectiveApprovalService;
import org.detective.services.detective.DetectiveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DetectiveApprovalService detectiveApprovalService;

    @Autowired
    private DetectiveApprovalRepository approvalRepository;

    @Autowired
    private DetectiveService detectiveService;
    @Autowired
    private DetectiveRepository detectiveRepository;

    @GetMapping("/approvals")
    public List<DetectiveApprovalDTO> getAllDetectiveApprovals() {

        return detectiveApprovalService.findAll();
    }

    @PostMapping("/approvals/update")
    public ResponseEntity<String> updateApprovalStatus(@RequestBody DetectiveApprovalDTO request) {
        Long id = request.getId();
        Long DeId = request.getDetectiveId();
        String status = request.getApprovalStatus();
        String message = request.getRejReason();

        // 처리 로직
        System.out.println("Approval ID: " + id + ", Status: " + status +"deid"+DeId);
        System.out.println("-------------------------------------------------------");

        // id로 엔티티 조회
        DetectiveApproval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found with id: " + id));

        if(status.equals("accept")){
            // 요청으로 받은 데이터로 엔티티 업데이트
            approval.setApprovalStatus(ApprovalStatus.APPROVED);
            approval.setRejReason(message);

            // 업데이트된 엔티티 저장
            approvalRepository.save(approval);

            //탐정도 update
            Detective detective = detectiveService.getDetectiveById(DeId);
            detective.setApprovalStatus(ApprovalStatus.APPROVED);
            detectiveRepository.save(detective);

            return ResponseEntity.ok(status);

        }else{
            // 요청으로 받은 데이터로 엔티티 업데이트
            approval.setApprovalStatus(ApprovalStatus.REJECTED);
            approval.setRejReason(message);

            // 업데이트된 엔티티 저장
            approvalRepository.save(approval);

            //탐정도 update
            Detective detective = detectiveService.getDetectiveById(DeId);
            detective.setApprovalStatus(ApprovalStatus.REJECTED);
            detectiveRepository.save(detective);
            return ResponseEntity.ok(status);
        }


    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


}
