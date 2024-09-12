package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyDTO {
    private Long requestId;
    private String email;
    private String title;
    private String description;
    private int price;
}
