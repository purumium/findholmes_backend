package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
//고객이 보낸 의뢰의 상세 정보를 조회하는 DTO
public class RequestDetailDTO {
    private Long requestId;

    private String speciality;
    private String location;
    private String title;
    private String description;
    private String gender;

    private LocalDateTime createAt;

    private Boolean status;

}
