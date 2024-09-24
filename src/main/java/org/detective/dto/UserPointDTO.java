package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPointDTO {

    private Long pointId;
    private Long userId;
    private String pointUsingType;
    private Long pointChangeAmount;

}
