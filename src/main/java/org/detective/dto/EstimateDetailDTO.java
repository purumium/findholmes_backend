package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
// 고객의 의뢰에 대한 답변서 상세정보를 저장하는 DTO
public class EstimateDetailDTO {
    private Long estimateId;
    private Long detectiveId;
    private String detectiveName;
    private String description;
    private int price;
    private String title;
    private LocalDateTime createAt;
    private String gender;
    private List<String> speciality;
    private String location;
    private String profileImg;
}
