package org.detective.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection="CHATROOMS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    private String id;

    private Long estimateId;

    private List<Participant> participants;

    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Participant {
        private Long userId;
        private String role;
    }

    public ChatRoom(Long estimateId, List<Participant> participants) {
        this.estimateId = estimateId;
        this.participants = participants;
    }
}
