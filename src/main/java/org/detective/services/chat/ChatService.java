package org.detective.services.chat;

import lombok.RequiredArgsConstructor;
import org.detective.entity.Chat;
import org.detective.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat saveMessage(Chat message) {
        chatRepository.save(message);
        return message;
    }

    public List<Chat> getMessageFromRoomId(String chatRoomId){
        return chatRepository.findByChatRoomId(chatRoomId);
    }

}
