package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.detective.entity.Speciality;

import java.util.List;

@Data
@AllArgsConstructor
public class DetectiveSimpleDTO {
    private Long detectiveId;
    private String detectiveName;
    private String gender;
    private List<Speciality> specialities;
    private String location;
    private String detectiveImg;
}
