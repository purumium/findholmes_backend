package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.detective.entity.Speciality;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
//탐정이 자신이 받은 의뢰들을 조회하는 DTO
public class RequestReceiveDTO {
    private Long assignedRequestId;
    private Long requestId;
    private String title;
    private String location;
    private LocalDateTime createAt;
    private Speciality speciality;
    private String status;
}
