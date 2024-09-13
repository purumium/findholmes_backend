package org.detective.controller.Receive;

import org.detective.dto.AssignedRequestDTO;
import org.detective.dto.EstimateDTO;
import org.detective.dto.ReceiveRequestDTO;
import org.detective.repository.EstimateRepository;
import org.detective.services.Estimate.EstimateService;
import org.detective.services.Receive.ReceiveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receive")
public class ReceiveController {

    private ReceiveService receiveService;
    private EstimateService estimateService;

    public ReceiveController(ReceiveService receiveService, EstimateService estimateService) {
        this.receiveService = receiveService;
        this.estimateService = estimateService;
    }



    @GetMapping("") //의뢰 요청서 목록, 탐정이 자산에게 할당된 요청서를 출력하는 컨트롤러
    public List<ReceiveRequestDTO> getAssingedRequest(@RequestParam(value = "email",required = true) String email) {
        System.err.println("Receive Controller");
        List<ReceiveRequestDTO> assignedRequest = receiveService.getAssignedRequest(email);
        System.out.println("Receive Controller : " + assignedRequest.toString());
        System.out.println("Receive Controller : " + assignedRequest.size());
        return assignedRequest;
    }

    @GetMapping("/detail") // 요청서 정보 상세보기
    public AssignedRequestDTO getRequestDetail(@RequestParam("requestId") Long requestId) {
        System.err.println("Receive Controller2");
        return receiveService.getRequestDetail(requestId);
    }

    @GetMapping("/estimate")
    public List<EstimateDTO> receivedEstimate(@RequestParam(value = "email",required = true) String email) {
        System.out.println("Receive Controller3");
        return estimateService.getReceivedEstimate(email);
    }
}
