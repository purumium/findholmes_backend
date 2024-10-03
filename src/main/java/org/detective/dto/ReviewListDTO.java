package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewListDTO {
    private Long id;
    private Long detectiveId;
    private int rating;
    private String content;
    private LocalDateTime updatedAt;
}
