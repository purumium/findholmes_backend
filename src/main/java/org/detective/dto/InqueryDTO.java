package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.detective.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InqueryDTO {
    private Long inqueryId;
    private Long writerId;
    private String title;
    private String email;
    private String category;
    private String content;
    private String responseStatus;
    private LocalDateTime createdAt;
}

