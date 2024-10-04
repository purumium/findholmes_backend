package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MyReviewListDTO {
    private Long id;
    private String content;
    private int rating;
    private Long clientId;
    private LocalDateTime updatedAt;
    private String estimateTitle;
    private String detectiveName;
}
