package org.detective.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.detective.entity.Specialty;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class RequestDTO {
    private String email;
    private String location;
    private String gender;
    private Long specialty;
    private String description;
}
