package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EstimateDetailDTO {
    private Long estimateId;
    private Long detectiveId;
    private String detectiveName;
    private String description;
    private int price;
    private String title;
    private LocalDateTime createAt;
}
