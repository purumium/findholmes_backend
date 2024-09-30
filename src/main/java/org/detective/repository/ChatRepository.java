package org.detective.repository;


import org.detective.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findByChatRoomId(String chatRoomId);

    Optional<Chat> findFirstByChatRoomIdOrderBySendTimeDesc(String chatRoomId);

    @Query("{ 'chatRoomId': ?0, 'isRead': false }")
    List<Chat> findUnreadMessagesByChatRoomId(String chatRoomId);
}
