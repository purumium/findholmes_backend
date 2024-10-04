package org.detective.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "CHAT_NOTIFICATIONS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatNotification {

    @Id
    private String id;

    @Field("chatroom_id")
    private String chatRoomId;

    @Field("user_id")
    private Long userId;

    @Field("notification")
    @Builder.Default
    private int notification = 0;

    public ChatNotification(String chatRoomId, Long userId) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
    }

    public void increaseNotification() {
        this.notification++;
    }

    public void readNotification() {
        this.notification = 0;
    }
}
