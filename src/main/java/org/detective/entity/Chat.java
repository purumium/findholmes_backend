package org.detective.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "CHATS")
@Data
@Builder
public class Chat {

    @Id
    private String id;

    @Field("chat_room_id")
    private String chatRoomId;

    @Field("sender_id")
    private Long senderId;

    @Field("message")
    private String message;

    @Field("send_time")
    @Indexed(name = "send_time_idx")
    private LocalDateTime sendTime;


}
