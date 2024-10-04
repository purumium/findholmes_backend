package org.detective.controller.Chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.detective.dto.ChatReadInfo;
import org.detective.entity.Chat;
import org.detective.repository.ChatRepository;
import org.detective.services.chat.ChatNotificationService;
import org.detective.services.chat.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatNotificationService chatNotificationService;

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    // /send로 메시지를 반환합니다.
    @MessageMapping("/receive")
    public void SocketHandler(Chat chatMessage) {
         chatService.saveMessage(chatMessage);
    }

    // 채팅 내역 불러오기
    @GetMapping("/chat/chatroom/{chatRoomId}")
    public List<Chat> getMessages(@PathVariable String chatRoomId) {
        // 채팅방 ID에 해당하는 모든 메시지 반환
        return chatService.getMessageFromRoomId(chatRoomId);
    }

    // 채팅 읽음 처리
    @MessageMapping("/read/{chatRoomId}")
    public void markMessageAsRead(ChatReadInfo readInfo) {
        chatService.markMessageAsRead(readInfo.getChatRoomId(), readInfo.getUserId());
    }

    @GetMapping("/chatConut")
    public int totalChatConut(@RequestParam Long userId) {
        return chatNotificationService.totalChatCount(userId);
    }

}

