package org.detective.dto;

import lombok.Data;

@Data
public class UserCountDTO {
    private String createdAt;
    private String role;
    private Long count;

    public UserCountDTO(String createdAt, String role, Long count) {
        this.createdAt = createdAt;
        this.role = role;
        this.count = count;
    }
}
