package org.detective.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDTO {
    private Long senderId;
    private Long receiverId;
    private String title;
    private String senderName;
    private String url;

    public NotificationDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }
    public NotificationDTO(Long senderId, Long receiverId, String title, String url) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.title = title;
        this.url = url;
    }
}
