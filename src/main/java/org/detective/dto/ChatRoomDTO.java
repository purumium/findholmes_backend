package org.detective.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // 파라미터 없는 기본 생성자
@AllArgsConstructor // 모든 필드를 받는 생성자
public class ChatRoomDTO {
    private String chatRoomId;                  // 채팅방 ID
    private List<ParticipantDTO> participants;  // 참가자 목록 (ParticipantDto 리스트)
    private String lastMessage;
    private LocalDateTime lastChatTime;
    private int notificationCount;
}

