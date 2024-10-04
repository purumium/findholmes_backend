package org.detective.repository;

import org.detective.entity.ChatNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatNotificationRepository extends MongoRepository<ChatNotification, String> {
    Optional<ChatNotification> findByUserIdAndChatRoomId(Long userId, String chatRoomId);

    List<ChatNotification> findByUserId(Long userId);

}