package org.detective.services.chat;

import lombok.RequiredArgsConstructor;
import org.detective.entity.Chat;
import org.detective.entity.ChatRoom;
import org.detective.repository.ChatRepository;
import org.detective.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public Chat saveMessage(Chat message) {
        chatRepository.save(message);
        return message;
    }

    public List<Chat> getMessageFromRoomId(String chatRoomId){
        return chatRepository.findByChatRoomId(chatRoomId);
    }

}
