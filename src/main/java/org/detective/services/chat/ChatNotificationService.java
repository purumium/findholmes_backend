package org.detective.services.chat;

import lombok.RequiredArgsConstructor;
import org.detective.entity.ChatNotification;
import org.detective.entity.ChatRoom;
import org.detective.repository.ChatNotificationRepository;
import org.detective.repository.ChatRoomRepository;
import org.detective.repository.TotalMessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatNotificationService {

    private final ChatNotificationRepository chatNotificationRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // 알림을 증가시키거나 초기화하고, STOMP를 통해 전송하는 메서드
    @Transactional
    public void sendNotification(Long userId, String chatRoomId, int readCount) {
        // 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        // 수신자 찾기 (발신자가 아닌 참여자)
        ChatRoom.Participant recipient = chatRoom.getParticipants().stream()
                .filter(participant -> !participant.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        if (recipient != null) {
            Long recipientUserId = recipient.getUserId();
            Optional<ChatNotification> optionalNotification = chatNotificationRepository.findByUserIdAndChatRoomId(recipientUserId, chatRoomId);
            ChatNotification notification;

            notification = optionalNotification.orElseGet(() -> new ChatNotification(chatRoomId, recipientUserId));
            notification.increaseNotification();

            // 알림 저장
            chatNotificationRepository.save(notification);
            // 알림 데이터를 STOMP로 전송
            messagingTemplate.convertAndSendToUser(
                    recipientUserId.toString(),              // 수신자 ID
                    "notifications",                 // 구독 경로 (/user/{userId}/notifications)
                    notification                            // 전송할 데이터 (ChatNotification 객체)
            );
        }
    }

    public int totalChatCount(Long userId) {
        List<ChatNotification> chatCount = chatNotificationRepository.findByUserId(userId);
        System.err.println(" 채팅 카운트 서비스 : "+chatCount+" 채팅 카운트 서비스 : "+chatCount +" 채팅 카운트 서비스 : "+chatCount +" 채팅 카운트 서비스 : "+chatCount +" 채팅 카운트 서비스 : "+chatCount +" 채팅 카운트 서비스 : "+chatCount +" 채팅 카운트 서비스 : "+chatCount +" 채팅 카운트 서비스 : "+chatCount );
        int totalCount = 0;
        for (ChatNotification mapping : chatCount) {
            totalCount+=mapping.getNotification();
        }
        return totalCount;
    }
}

