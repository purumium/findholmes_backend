package org.detective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Notifications")
public class Notification {
    @Id
    @Column(name="notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name ="url")
    private String url;

    @Column(name = "description")
    private String description;

    @Column(name= "notification_time")
    private LocalDateTime notificationTime=LocalDateTime.now();

    @Column(name= "is_check")
    private Boolean isCheck=false;

    public Notification(Long senderId, Long receiverId, String url, String description) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.url = url;
        this.description = description;
    }
}
