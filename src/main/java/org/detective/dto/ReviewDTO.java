package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long detectiveId;
    private Double rating;
    private String content;
    private Long estimateId;
}
