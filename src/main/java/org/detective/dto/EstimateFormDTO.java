package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// 고객의 요청에 대해 작성한 답변서의 정보를 저장하는 DTO
public class EstimateFormDTO {
    private Long requestId;
    private String email;
    private String title;
    private String description;
    private int price;
}
