package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.detective.entity.Speciality;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
// 고객이 자신이 보낸 의뢰들을 조회하는 DTO
public class RequestListDTO {
    private Long requestId;
    private String title;
    private LocalDateTime createAt;
    private String speciality;
    private boolean status;
}
