package org.detective.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field("estimate_id")
    private Long estimateId;

    @Field("participants")
    private List<Participant> participants;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("chat_count")
    @Builder.Default
    private int chatCount = 0;

    @Field("unlimited_access")
    @Builder.Default
    private boolean unlimitedAccess = false;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Participant {

        @Field("user_id")
        private Long userId;

        @Field("role")
        private String role;

        @Field("accepted_privacy")
        private Boolean acceptedPrivacy;

    }

    public void increaseChatCount() {
        this.chatCount++;
    }
    public void enableUnlimitedAccess() {
        this.unlimitedAccess = true;
    }

}
