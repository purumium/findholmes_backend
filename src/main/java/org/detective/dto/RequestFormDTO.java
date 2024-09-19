package org.detective.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 고객이 의뢰를 보낼 때 사용하는 DTO
public class RequestFormDTO {
    private String email;
    private String location;
    private String gender;
    private Long speciality;
    private String title;
    private String description;
    private Long detectiveId; // detectiveId가 null이면  random으로 5명의 탐정에게 의뢰요청, null이 아니면 탐정에게 직접 의뢰요청
}
