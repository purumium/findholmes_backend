package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEstimateDTO {
    private String requestTitle;
    private String estimateTitle;
    private String detectiveName;

}
