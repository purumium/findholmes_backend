package org.detective.controller.Request;

import org.detective.dto.*;
import org.detective.services.Request.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("") // 고객이 탐정에게 요청을 보내는 메서드
    public ResponseEntity<String> createRequest(@RequestBody RequestFormDTO requestFormDTO) {
        System.err.println("Controller : createRequest 실행");
        System.err.println("Controller RequestDTO : "+ requestFormDTO);
        try {
            System.err.println("Controller : Try createRequest");
            requestService.createRequest(requestFormDTO);
            return ResponseEntity.ok("Request Created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list") // 고객이 자신이 보낸 요청서의 목록을 불러오는 메서드
    public ResponseEntity<List<RequestListDTO>> getAllRequests(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(requestService.getAllRequests(userId));
    }

    @GetMapping("/detail") // 요청서 정보 상세보기
    public ResponseEntity<RequestDetailDTO> getRequestDetail(@RequestParam("requestId") Long requestId) {
        return ResponseEntity.ok(requestService.getRequestDetail(requestId));
    }

    @GetMapping("/receive") // 탐정이 요청받은 의뢰목록을 조회하는 메서드
    public ResponseEntity<List<RequestReceiveDTO>> getRequestsReceive(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(requestService.getRequestsReceive(userId));
    }
}
