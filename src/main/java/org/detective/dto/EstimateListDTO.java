package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
// 탐정이 자신이 보낸 답변서와 답변서와 매칭되는 의뢰서를 저장하는 DTO
public class EstimateListDTO {
    private Long requestId;
    private Long estimateId;

    private String requestTitle;
    private String estimateTitle;
    private String location;
    private String speciality;

    private LocalDateTime requestCreateAt;
    private LocalDateTime estimateCreateAt;


    private String detectiveName;

}
