package org.detective.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.detective.entity.Speciality;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class RequestDTO {
    private String email;
    private String location;
    private String gender;
    private Long speciality;
    private String description;
}
