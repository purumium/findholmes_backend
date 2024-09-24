package org.detective.repository;

import org.detective.entity.ChatRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatRoomRepositoryTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    void testSaveChatRoom() {
        // Given: Participant 리스트 생성
        ChatRoom.Participant participant1 = new ChatRoom.Participant(10L, "c", false);
        ChatRoom.Participant participant2 = new ChatRoom.Participant(12L, "d", false);
        List<ChatRoom.Participant> participants = List.of(participant1, participant2);

        // ChatRoom 객체 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .estimateId(100L)
                .participants(participants)
                .build();

        // When: ChatRoom 저장
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // Then: 저장된 ChatRoom이 제대로 저장되었는지 확인
        assertThat(savedChatRoom.getId()).isNotNull();  // ID는 저장 후 생성됨
        assertThat(savedChatRoom.getEstimateId()).isEqualTo(100L);
        assertThat(savedChatRoom.getParticipants().size()).isEqualTo(2);
        assertThat(savedChatRoom.getParticipants().get(0).getUserId()).isEqualTo(10L);
        assertThat(savedChatRoom.getParticipants().get(1).getRole()).isEqualTo("d");
    }
}