package org.detective.repository;


import org.detective.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findByChatRoomId(String chatRoomId);

    void deleteByChatRoomId(String chatRoomId);
}
