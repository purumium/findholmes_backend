package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.detective.entity.Speciality;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReceiveRequestDTO {
    private Long assignedRequestId;
    private Long requestId;
    private String title;
    private String location;
    private LocalDateTime createAt;
    private Speciality speciality;
}
