package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.detective.entity.Speciality;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssignedRequestDTO {
    private Long assignedRequestId;
    private Long detectiveId;
    private Long clientId;
    private Long userId;
    private Long requestId;

    private String userName;
    private String description;
    private String location;

    private LocalDateTime createAt;

    private Speciality speciality;
}
