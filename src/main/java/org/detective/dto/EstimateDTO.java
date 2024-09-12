package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.detective.entity.Speciality;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EstimateDTO {
    Long estimateId;
    Long requestId;
    String title;
    LocalDateTime createAt;
    String speciality;
}
