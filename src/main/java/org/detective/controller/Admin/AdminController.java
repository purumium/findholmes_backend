package org.detective.controller.Admin;

import org.detective.controller.Inquery.InqueryController;
import org.detective.dto.DetectiveApprovalDTO;
import org.detective.entity.ApprovalStatus;
import org.detective.entity.Detective;
import org.detective.entity.DetectiveApproval;
import org.detective.repository.DetectiveApprovalRepository;
import org.detective.repository.DetectiveRepository;
import org.detective.services.admin.DetectiveApprovalService;
import org.detective.services.detective.DetectiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final DetectiveApprovalService detectiveApprovalService;

    private final DetectiveApprovalRepository approvalRepository;

    private  final DetectiveService detectiveService;

    private  final DetectiveRepository detectiveRepository;

    private final InqueryController inqueryController;

    public AdminController( DetectiveApprovalService detectiveApprovalService,
                            DetectiveApprovalRepository approvalRepository,
                            DetectiveService detectiveService,
                            DetectiveRepository detectiveRepository,
                            InqueryController inqueryController) {
        this.detectiveApprovalService = detectiveApprovalService;
        this.approvalRepository = approvalRepository;
        this.detectiveService = detectiveService;
        this.detectiveRepository = detectiveRepository;
        this.inqueryController = inqueryController;
    }

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

    // 문의글 상세 보기
    @GetMapping("/inquery/{requestid}")
    public ResponseEntity<?> getInqueryById(@PathVariable("requestid") Long id) {
        return inqueryController.getInqueryById(id);
    }

    // 전체 문의 조회
    @GetMapping("/inquery/all")
    public ResponseEntity<?> getAllInqueries() {
        return inqueryController.getAllInqueries();
    }

    // 답변 대기 상태의 문의 조회
    @GetMapping("/inquery/pending")
    public ResponseEntity<?> getPendingInqueries() {
        return inqueryController.getPendingInqueries();
    }

    // 답변 완료 상태의 문의 조회
    @GetMapping("/inquery/complete")
    public ResponseEntity<?> getCompleteInqueries() {
        return inqueryController.getCompleteInqueries();
    }


    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


}
