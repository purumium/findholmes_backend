package org.detective.services.chat;

import lombok.RequiredArgsConstructor;
import org.detective.entity.Chat;
import org.detective.entity.ChatNotification;
import org.detective.entity.ChatRoom;
import org.detective.repository.ChatNotificationRepository;
import org.detective.repository.ChatRepository;
import org.detective.repository.ChatRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatNotificationRepository chatNotificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatNotificationService chatNotificationService;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅 보내기
    @Transactional
    public Chat saveMessage(Chat message) {

        chatRepository.save(message);

        // 채팅 알림 관련 로직
        String chatRoomId = message.getChatRoomId();
        Long userId = message.getSenderId();
        int readCount = message.getReadCount();

        chatNotificationService.sendNotification(userId, chatRoomId, readCount);
        return message;
    }

    // 채팅 메세지 불러오기
    public List<Chat> getMessageFromRoomId(String chatRoomId){
        return chatRepository.findByChatRoomId(chatRoomId);
    }

//    public void markMessageAsRead(String chatRoomId){
//        List<Chat> unreadMessages = chatRepository.findUnreadMessagesByChatRoomId(chatRoomId);
//
//        unreadMessages.forEach(message -> {
//            message.isRead();  // 읽음 상태로 업데이트
//            chatRepository.save(message);
//        });
//    }

    // 상대방이 메시지를 읽었을 때 readCount 증가
    @Transactional
    public void markMessageAsRead(String chatRoomId, Long userId) {
        List<Chat> messages = chatRepository.findByChatRoomId(chatRoomId);
        System.out.println("프론트에서 보내준거인데 ㅠㅠㅠㅠㅠ chatRoomId: " + chatRoomId + ", userId: " + userId);
        for (Chat message : messages) {
            // 발신자가 아닌 사람이 읽었을 때만 readCount 증가
            if (!message.getSenderId().equals(userId) && message.getReadCount() < 2) { // 상대방이 보낸거가 다 2가 됨
                message.isRead();  // readCount를 증가시키는 메서드 호출
                chatRepository.save(message);

                ChatNotification chatNotification = chatNotificationRepository.findByUserIdAndChatRoomId(userId, chatRoomId)
                        .orElseThrow(() -> new RuntimeException("해당 알림이 존재하지 않습니다."));
                chatNotification.readNotification();
                chatNotificationRepository.save(chatNotification);

                // 발신자에게 읽음 상태를 알림
                messagingTemplate.convertAndSend("/topic/chat/" + chatRoomId, message);
            }
        }
    }




}
