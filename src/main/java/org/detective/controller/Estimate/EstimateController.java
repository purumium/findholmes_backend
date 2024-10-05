package org.detective.controller.Estimate;

import org.detective.dto.EstimateDetailDTO;
import org.detective.dto.EstimateFormDTO;
import org.detective.dto.EstimateListDTO;
import org.detective.services.Estimate.EstimateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    private final EstimateService estimateService;
    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    @PostMapping("") // 고객의 의뢰에 대한 탐정의 답변서 작성 메서드
    public boolean createEstimate(@RequestBody EstimateFormDTO estimateFormDTO) {
        return estimateService.createEstimate(estimateFormDTO);

    }
    @GetMapping("/list") // 탐정(자신)이 보낸 답변서 목록 조회 메서드
    public List<EstimateListDTO> getEstimateList(@RequestParam(value = "userId") Long userId) {
        return estimateService.getEstimateList(userId);
    }

    @GetMapping("/details") // 고객이 보낸 의뢰애 대한 탐정의 답변서 상세정보 조회메서드
    public ResponseEntity<EstimateListDTO> getEstimateDetail(@RequestParam("userId") Long userId, @RequestParam("requestId") Long requestId) {
        return ResponseEntity.ok(estimateService.getEstimateDetail(userId, requestId));
    }

    @GetMapping("/receivelist")
    public ResponseEntity<List<EstimateDetailDTO>> getReceiveEstimateList(@RequestParam("requestId") Long requestId) {
        return ResponseEntity.ok(estimateService.getReceiveEstimateList(requestId));
    }

}
