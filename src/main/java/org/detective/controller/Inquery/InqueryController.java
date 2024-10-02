package org.detective.controller.Inquery;

import org.detective.controller.Member.MemberController;
import org.detective.dto.InqueryDTO;
import org.detective.entity.Inquery;
import org.detective.entity.User;
import org.detective.services.inquery.InqueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inquery")
public class InqueryController {

    private final InqueryService inqueryService;
    private final MemberController memberController;

    public InqueryController(InqueryService inqueryService, MemberController memberController) {
        this.inqueryService = inqueryService;
        this.memberController = memberController;
    }

    // 문의글 저장
    @PostMapping("/insert")
    public ResponseEntity<?> saveInquery(@RequestBody InqueryDTO inqueryDTO) {
        try {
            User user = memberController.getUserInfo();

            Inquery inquery = new Inquery(
                    null,  // id는 자동 생성되므로 null로 설정
                    user,
                    inqueryDTO.getTitle(),
                    inqueryDTO.getEmail(),
                    inqueryDTO.getCategory(),
                    inqueryDTO.getContent(),
                    Inquery.ResponseStatus.PENDING,
                    null
            );
            Inquery result = inqueryService.saveInquery(inquery);

            return ResponseEntity.ok("문의가 성공적으로 접수됨");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("문의 접수 에러 발생함 " + e.getMessage());
        }
    }

    // 문의글 상세 보기
    @GetMapping("/{requestid}")
    public ResponseEntity<?> getInqueryById(@PathVariable("requestid") Long id) {
        Inquery inquery = inqueryService.getInqueryById(id);

        InqueryDTO inquiryDTO = new InqueryDTO(
                inquery.getId(),
                inquery.getUser().getUserId(),
                inquery.getTitle(),
                inquery.getEmail(),
                inquery.getCategory(),
                inquery.getContent(),
                inquery.getResponseStatus().name(),
                inquery.getCreatedAt()
        );
        return ResponseEntity.ok(inquiryDTO);
    }


    // 전체 문의글 조회
    @GetMapping("/all")
    public ResponseEntity<?> getAllInqueries() {
        try {
            List<Inquery> inqueries = inqueryService.getAllInqueries();

            List<InqueryDTO> inqueryDTOS= inqueries.stream()
                    .map(this::convertToInqueryDTO)  // DTO로 변환하기 위한 작업
                    .collect(Collectors.toList());

            return ResponseEntity.ok(inqueryDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("문의글 전체 불러오기 에러" + e.getMessage());
        }
    }

    // 답변 상태에 따른 조회(답변대기)
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingInqueries() {
        try {
            List<Inquery> inqueries = inqueryService.getInqueriesByStatus(Inquery.ResponseStatus.PENDING);

            List<InqueryDTO> inqueryDTOS = inqueries.stream()
                    .map(this::convertToInqueryDTO)  // DTO로 변환하기 위한 작업
                    .collect(Collectors.toList());

            return ResponseEntity.ok(inqueryDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("문의글 답변대기 불러오기 에러" + e.getMessage());
        }
    }

    // 답변 상태에 따른 조회(답변완료)
    @GetMapping("/complete")
    public ResponseEntity<?> getCompleteInqueries() {
        try {
            List<Inquery> inqueries = inqueryService.getInqueriesByStatus(Inquery.ResponseStatus.COMPLETE);

            List<InqueryDTO> inqueryDTOS = inqueries.stream()
                    .map(this::convertToInqueryDTO)  // DTO로 변환하기 위한 작업
                    .collect(Collectors.toList());

            return ResponseEntity.ok(inqueryDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("문의글 답변완료 불러오기 에러" + e.getMessage());
        }
    }

    // 문의글 상태 업데이트
    @GetMapping("/{requestid}/status")
    public ResponseEntity<?> updateInqueryStatus(@PathVariable("requestid") Long id) {
        try {
            inqueryService.updateInqueryStatsus(id);

            return ResponseEntity.ok("문의 상태가 업데이트 되었습니다");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("문의글 상태 업데이트 에러" + e.getMessage());
        }
    }

    // 사용자별 목록
    @GetMapping("/listbyuser")
    public ResponseEntity<?> getInqueryListByUser() {
        User user = memberController.getUserInfo();

        try {
            List<Inquery> inqueryList = inqueryService.getInqueryByUser(user);

            List<InqueryDTO> inqueryDTOS = inqueryList.stream()
                    .map(this::convertToInqueryDTO)  // DTO로 변환하기 위한 작업
                    .collect(Collectors.toList());

            return ResponseEntity.ok(inqueryDTOS);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("문의글 불러오기 에러" + e.getMessage());
        }
    }



    // 엔티티 => DTO로 반환
    private InqueryDTO convertToInqueryDTO(Inquery inquery) {
        InqueryDTO inqueryDTO = new InqueryDTO(
                inquery.getId(),
                inquery.getUser().getUserId(),
                inquery.getTitle(),
                inquery.getEmail(),
                inquery.getCategory(),
                inquery.getContent(),
                inquery.getResponseStatus().name(),
                inquery.getCreatedAt()
        );
        return inqueryDTO;
    }




}