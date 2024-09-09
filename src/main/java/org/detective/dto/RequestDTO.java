package org.detective.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String email;
    private String location;
    private String gender;
    private Long speciality;
    private String description;
}