package org.detective.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "CHATS")
@Data
@Builder
public class Chat {

    @Id
    private String id;

    private String chatRoomId;

    private Long senderId;

    private String message;

    private LocalDateTime sendTime;


}
