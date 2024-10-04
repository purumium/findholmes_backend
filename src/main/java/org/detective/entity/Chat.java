package org.detective.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "CHATS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    private String id;

    @Field("chatroom_id")
    private String chatRoomId;

    @Field("sender_id")
    private Long senderId;

    @Field("message")
    private String message;

    @Field("send_time")
    @Indexed(name = "send_time_idx")
    private LocalDateTime sendTime;

    @Field("read_count")
    @Builder.Default
    private int readCount = 1;

    public void isRead() {
        this.readCount++;
    }


}
